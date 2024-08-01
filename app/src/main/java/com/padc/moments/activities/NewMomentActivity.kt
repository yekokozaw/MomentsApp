package com.padc.moments.activities

import android.Manifest
import android.adservices.topics.Topic
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.padc.moments.adapters.NewMomentImageAdapter
import com.padc.moments.data.vos.MomentVO
import com.padc.moments.data.vos.UserVO
import com.padc.moments.databinding.ActivityNewMomentBinding
import com.padc.moments.databinding.BottoomSheetDialogChooseImageBinding
import com.padc.moments.mvp.impls.NewMomentPresenterImpl
import com.padc.moments.mvp.interfaces.NewMomentPresenter
import com.padc.moments.mvp.views.NewMomentView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.padc.moments.network.storage.getAccessToken
import com.padc.moments.network.storage.sendMessageToTopic
import com.padc.moments.utils.makeToast
import com.padc.moments.utils.show
import com.theartofdev.edmodo.cropper.CropImage
import java.io.IOException

class NewMomentActivity : AppCompatActivity(), NewMomentView {

    private lateinit var binding: ActivityNewMomentBinding
    // Adapter
    private lateinit var mAdapter: NewMomentImageAdapter
    private var mGrade : String = ""

    // Presenter
    private lateinit var mPresenter: NewMomentPresenter

    // General
    private lateinit var imageUri : Uri
    private var userName: String = ""
    private var userProfileImage: String = ""
    private var userId:String = ""
    private lateinit var dialog:BottomSheetDialog

    private val requestGalleryPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            chooseImageFromGallery()
        } else {
            showError("Permission denied")
        }
    }

//    private val cropActivityResultContract = object : ActivityResultContract<Any?,Uri?>(){
//        override fun createIntent(context: Context, input: Any?): Intent {
//            return CropImage.activity()
//                .setAspectRatio(16,18)
//                .getIntent(this@NewMomentActivity)
//
//        }
//
//        override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
//            return CropImage.getActivityResult(intent)?.uri
//        }
//    }

    private val openGalleryLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if(result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val selectedImageUri: Uri? = data?.data
            if (selectedImageUri != null){
                imageUri = selectedImageUri
                uploadImage()
            }else{
                makeToast(this,"invalid image")
            }
        }
        else
            makeToast(this,"invalid image")
    }

    companion object {
        private const val GRADE = "grade"
        fun newIntent(context: Context,grade : String): Intent {
            val intent = Intent(context, NewMomentActivity::class.java)
            intent.putExtra(GRADE,grade)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewMomentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpPresenter()
        setUpListeners()
        setUpRecyclerView()
        mGrade = intent.getStringExtra(GRADE).toString()

        mPresenter.getMomentType(mGrade)
        mPresenter.onUIReady( this)
        getTokenByGroupType()
    }

    private fun getTokenByGroupType() {
        when(mGrade){
            "FirstYear" -> mPresenter.getTokenByGroup("first")
            "SecondYear" -> mPresenter.getTokenByGroup("second")
            "ThirdYear" -> mPresenter.getTokenByGroup("third")
            "FourthYear" -> mPresenter.getTokenByGroup("fourth")
            "FifthYear" -> mPresenter.getTokenByGroup("fifth")
            "FinalYear" -> mPresenter.getTokenByGroup("final")
        }
    }

    private fun setUpPresenter() {
        mPresenter = ViewModelProvider(this)[NewMomentPresenterImpl::class.java]
        mPresenter.initPresenter(this)
    }

    private fun setUpListeners() {
        binding.btnBackNewMoment.setOnClickListener {
            mPresenter.onTapBackButton()
        }

        binding.btnCreateNewMoment.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            val body = binding.etPostNewMoment.text.toString()
            mPresenter.onTapCreateButton(getMomentPost(),userName,body, grade = mGrade)
            mPresenter.clearMomentImages()

        }
    }

    private fun getMomentPost(): MomentVO {
        val caption = binding.etPostNewMoment.text.toString()
        val createLike = "0"
        val commentCount = 0
        val likedList = emptyMap<String,String>()
        return MomentVO(
            System.currentTimeMillis().toString(),
            userId,
            userName,
            userProfileImage,
            caption,
            mPresenter.getMomentImages().dropLast(1),
            likedList,
            createLike,
            commentCount = commentCount
        )
    }

    private fun setUpRecyclerView() {
        mAdapter = NewMomentImageAdapter(mPresenter)
        binding.rvBackground.adapter = mAdapter
        binding.rvBackground.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    override fun navigateToPreviousScreen() {
        finish()
    }

    private fun uploadImage(){
        val filePath = imageUri

        mAdapter.setNewData(filePath.toString())
        try {
            filePath.let { fileUrl ->
                if (Build.VERSION.SDK_INT >= 29) {
                    val source = ImageDecoder.createSource(this.contentResolver, fileUrl)
                    val bitmapImage = ImageDecoder.decodeBitmap(source)
                    mPresenter.createMomentImages(bitmapImage)
                } else {
                    val bitmapImage = MediaStore.Images.Media.getBitmap(
                        applicationContext.contentResolver, fileUrl
                    )
                    mPresenter.createMomentImages(bitmapImage)
                }
            }
            dialog.dismiss()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun showGallery() {

        dialog = BottomSheetDialog(this)

        val dialogBinding = BottoomSheetDialogChooseImageBinding.inflate(layoutInflater)

        dialog.setContentView(dialogBinding.root)
        dialog.setCancelable(true)

        dialogBinding.btnChooseFromGalleryRegister.setOnClickListener {
                if (ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.READ_MEDIA_IMAGES
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    // Permission already granted, open the gallery
                    chooseImageFromGallery()
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
                        requestGalleryPermissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
                    }else{
                        requestGalleryPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                    }
                }
        }

        dialogBinding.btnCancelBottomSheetDialog.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    override fun finishFragment(topic: String) {
        getAccessToken(this){token ->
            sendMessageToTopic(token,topic,userName,binding.etPostNewMoment.text.toString())
        }
        binding.progressBar.visibility = View.GONE
        Toast.makeText(this, "Successfully uploaded!", Toast.LENGTH_SHORT).show()
        finish()
    }

    private fun chooseImageFromGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        openGalleryLauncher.launch(galleryIntent)
    }

    override fun showUserInformation(userList: List<UserVO>) {
        for (user in userList) {
            if (mPresenter.getUserId() == user.userId) {

                userId = user.userId
                userName = user.userName
                userProfileImage = user.imageUrl

                binding.tvUserNameNewMoment.text = user.userName

                Glide.with(this)
                    .load(user.imageUrl)
                    .into(binding.ivProfileImageNewMoment)
            }
        }
    }

    override fun imageReady(ready: Boolean) {
        binding.ivReadyImage.show()
        Toast.makeText(this, "Image is ready", Toast.LENGTH_SHORT).show()
    }


    override fun showError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }
}