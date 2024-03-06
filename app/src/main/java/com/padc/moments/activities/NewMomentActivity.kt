package com.padc.moments.activities

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
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
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.UUID

class NewMomentActivity : AppCompatActivity(), NewMomentView {

    private lateinit var binding: ActivityNewMomentBinding
    // Adapter
    private lateinit var mAdapter: NewMomentImageAdapter
    private var mGrade : String = ""

    // Presenter
    private lateinit var mPresenter: NewMomentPresenter

    // General
    private val REQUEST_CODE_GALLERY = 0
    private val REQUEST_IMAGE_CAPTURE = 1
    private var userName: String = ""
    private var userProfileImage: String = ""
    private var userId:String = ""
    private lateinit var dialog:BottomSheetDialog

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
        mPresenter.getTokenByGroup(group = "second")
        mPresenter.onUIReady( this)
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
            finish()
        }
    }

    private fun getMomentPost(): MomentVO {
        val caption = binding.etPostNewMoment.text.toString()
        val createLike = "0"
        val likedList = emptyMap<String,String>()
        return MomentVO(
            System.currentTimeMillis().toString(),
            userId,
            userName,
            userProfileImage,
            caption,
            mPresenter.getMomentImages().dropLast(1),
            likedList,
            createLike
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

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if ((requestCode == REQUEST_CODE_GALLERY || requestCode == REQUEST_IMAGE_CAPTURE) && resultCode == Activity.RESULT_OK) {

            val filePath = data?.data

            if(requestCode == REQUEST_IMAGE_CAPTURE) {
                Toast.makeText(this, "You take a photo", Toast.LENGTH_SHORT).show()
                val imageBitmap = data?.extras?.get("data") as Bitmap
                mPresenter.createMomentImages(imageBitmap)

                val bitmap: Bitmap = imageBitmap
                val byteArrayOutputStream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
                val byteArray: ByteArray = byteArrayOutputStream.toByteArray()

                val file = File(applicationContext.cacheDir, "img${UUID.randomUUID()}.jpg")
                file.createNewFile()

                val fileOutputStream = FileOutputStream(file)
                fileOutputStream.write(byteArray)
                fileOutputStream.flush()
                fileOutputStream.close()
                val uri: Uri = FileProvider.getUriForFile(applicationContext, applicationContext.packageName + ".provider", file)

                mAdapter.setNewData(uri.toString())
                dialog.dismiss()
                return
            }

            mAdapter.setNewData(filePath.toString())

            try {
                filePath?.let { fileUrl ->
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
    }

    override fun showGallery() {

        dialog = BottomSheetDialog(this)

        val dialogBinding = BottoomSheetDialogChooseImageBinding.inflate(layoutInflater)

        dialog.setContentView(dialogBinding.root)
        dialog.setCancelable(true)

        dialogBinding.btnTakePhotoRegister.setOnClickListener {

            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CAMERA
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // Request permission
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CAMERA),
                    REQUEST_IMAGE_CAPTURE
                )
            } else {
                // Permission already granted, open the gallery
                openCamera()
            }
        }

        dialogBinding.btnChooseFromGalleryRegister.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // Request permission
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    REQUEST_CODE_GALLERY
                )
            } else {
                // Permission already granted, open the gallery
                chooseImageFromGallery()
            }
        }

        dialogBinding.btnCancelBottomSheetDialog.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    override fun finishFragment() {
        binding.progressBar.visibility = View.GONE
        Toast.makeText(this, "Successfully uploaded!", Toast.LENGTH_SHORT).show()
        finish()
    }

    private fun chooseImageFromGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(
            Intent.createChooser(intent, "Select Upload Image"),
            REQUEST_CODE_GALLERY
        )
    }

    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent.resolveActivity(packageManager) != null) {
            startActivityForResult(
                Intent.createChooser(intent, "Select Upload Image"),
                REQUEST_IMAGE_CAPTURE
            )
        }
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


    override fun showError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }

}