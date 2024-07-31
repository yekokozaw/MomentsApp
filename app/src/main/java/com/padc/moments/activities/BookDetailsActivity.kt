package com.padc.moments.activities

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.padc.moments.R
import com.padc.moments.adapters.BookAdapter
import com.padc.moments.data.models.AuthenticationModel
import com.padc.moments.data.models.AuthenticationModelImpl
import com.padc.moments.data.models.MomentModel
import com.padc.moments.data.models.MomentModelImpl
import com.padc.moments.databinding.ActivityBookDetailsBinding
import com.padc.moments.databinding.CustomDialogBinding
import com.padc.moments.delegates.BookViewHolderDelegate
import com.padc.moments.utils.show
import com.padc.moments.utils.showSnackBar
import com.padc.moments.utils.showSuccessSnackBar
import com.padc.moments.workmanager.DownloadWorker

class BookDetailsActivity : AppCompatActivity(),BookViewHolderDelegate{

    private lateinit var mBinding: ActivityBookDetailsBinding
    private lateinit var mBookAdapter : BookAdapter
    private var mGender : String = ""
    private var mTitle : String = ""
    private var mFileUrl : String = ""
    private val mMomentModel : MomentModel = MomentModelImpl
    private val mAuthModel : AuthenticationModel = AuthenticationModelImpl
    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.data?.let { uri ->
                showTextInputDialog(uri)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityBookDetailsBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        val authData = mAuthModel.getToken()
        mGender = authData?.token ?: ""
        bindDataForTeacher()
        setUpRecyclerView()
        setUpNetworkCall()
        setUpListeners()
    }

    private fun bindDataForTeacher(){
        if (mGender == "teacher"){
            mBinding.fabAddBook.show()
            mBinding.tvInstruction.show()
        }
    }

    private fun setUpRecyclerView(){
        mBookAdapter = BookAdapter(this)
        mBinding.rvBook.adapter = mBookAdapter
        mBinding.rvBook.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
    }

    private fun setUpListeners(){
        mBinding.fabAddBook.setOnClickListener {
            selectPdfFile()
        }
    }

    private fun setUpNetworkCall(){
        mMomentModel.getPdfBooks(
            onSuccess = {
                mBookAdapter.setNewData(it)
            },
            onFailure = {
                Snackbar.make(mBinding.root,it,Snackbar.LENGTH_SHORT).show()
            }
        )
    }

    private fun checkPermissionsAndDownload() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // For Android 11 (API 29) and later
            startDownload()
        } else {
            // For earlier versions, still check WRITE_EXTERNAL_STORAGE permission
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    100
                )
            } else {
                startDownload()
            }
        }
    }

    private fun startDownload(){
        val data = Data.Builder()
            .putString("url",mFileUrl)
            .putString("title","$mTitle.pdf")
            .putString("type","application/pdf")
            .build()

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.NOT_ROAMING)
            .setRequiresStorageNotLow(true)
            .build()

        val downloadWorkRequest = OneTimeWorkRequestBuilder<DownloadWorker>()
            .setConstraints(constraints)
            .setInputData(data)
            .build()

        WorkManager.getInstance(applicationContext)
            .enqueue(downloadWorkRequest)

        WorkManager.getInstance(this).getWorkInfoByIdLiveData(downloadWorkRequest.id)
            .observe(this) { workInfo ->
                if (workInfo != null) {
                    when (workInfo.state) {
                        WorkInfo.State.SUCCEEDED -> {

                        }
                        WorkInfo.State.FAILED -> {
                            showSnackBar(mBinding.root,"Download is not successful")
                        }
                        WorkInfo.State.RUNNING -> {
                            Snackbar.make(mBinding.main,"Downloading ...",Snackbar.LENGTH_LONG).show()
                            //val progress = workInfo.progress.getInt("progress", 0)
                        }
                        else -> {}
                    }
                }
            }
    }

    private fun handlePdfUri(uri: Uri) {
        mMomentModel.uploadPdfFile(
            id = System.currentTimeMillis().toString(),
            title = mTitle,
            fileUri = uri,
            onSuccess = {

            },
            onFailure = {
                showSnackBar(mBinding.root,it)
            }
        )
    }

    private fun showDeleteBookDialog(id : String,title : String,fileUrl: String) {
        val dialog =
            MaterialAlertDialogBuilder(this,R.style.RoundedAlertDialog)
                .setTitle("CONFIRM DELETE")
                .setMessage("Are you sure to delete $title?")
                .setPositiveButton("Yes"){ delete,_ ->
                    delete.dismiss()
                    // Call your delete book method here
                    val storagePath = extractStoragePathFromUrl(fileUrl)
                    mMomentModel.deleteBook(
                        id,
                        storagePath,
                        onSuccess = {
                            showSuccessSnackBar(mBinding.root,it)
                        },
                        onFailure = {
                            showSnackBar(mBinding.root,it)
                        }
                    )
                }
                .setNegativeButton("No"){ delete,_ ->
                    delete.dismiss()
                }
                .create()
        dialog.show()
    }

    private fun extractStoragePathFromUrl(url: String): String {
        val uri = Uri.parse(url)
        val path = uri.path?.substringAfter("/o/")?.substringBefore("?alt=media")
        return path?.replace("%2F", "/") ?: ""
    }

    private fun selectPdfFile() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "application/pdf"
        resultLauncher.launch(intent)
    }

    override fun onTapPdfViewHolder(title: String, fileUrl: String) {
        val dialogBuilder = MaterialAlertDialogBuilder(this,R.style.RoundedAlertDialog)
            .setTitle(mTitle)
            .setMessage("Are you sure to Download?")
            .setPositiveButton("OK"){dialog,_ ->
                mTitle = title
                mFileUrl = fileUrl
                checkPermissionsAndDownload()
                dialog.dismiss()
            }
            .setNegativeButton("Cancel"){dialog,_->
                dialog.dismiss()
            }
        val alertDialog = dialogBuilder.create()
        alertDialog.show()
    }

    override fun onLongPressViewHolder(id : String,title: String, fileUrl: String) {
        if (mGender == "teacher"){
            showDeleteBookDialog(id,title,fileUrl)
        }
    }

    private fun showTextInputDialog(uri : Uri) {
        val dialogBinding = CustomDialogBinding.inflate(layoutInflater)

        // Create the dialog
        val dialog = AlertDialog.Builder(this,R.style.RoundedAlertDialog)
            .setTitle("Book Title")
            .setView(dialogBinding.root)
            .setPositiveButton("OK") { dialog, _ ->
                val inputText = dialogBinding.editTextDialog.text.toString()
                mTitle = inputText
                if (inputText.isEmpty()){
                    mTitle = System.currentTimeMillis().toString()
                }
                handlePdfUri(uri)
                showSnackBar(mBinding.root,"Uploading...")
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .create()

        dialog.show()

        dialog.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }
}