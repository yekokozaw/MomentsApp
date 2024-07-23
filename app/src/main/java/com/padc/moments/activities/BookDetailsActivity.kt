package com.padc.moments.activities

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
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
import com.padc.moments.databinding.ActivityBookDetailsBinding
import com.padc.moments.delegates.BookViewHolderDelegate
import com.padc.moments.workmanager.DownloadWorker

class BookDetailsActivity : AppCompatActivity(),BookViewHolderDelegate{

    private lateinit var mBinding: ActivityBookDetailsBinding
    private lateinit var mBookAdapter : BookAdapter
    private var mTitle : String = ""
    private var fileUrl : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityBookDetailsBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_book_details)

        setUpRecyclerView()
    }

    private fun setUpRecyclerView(){
        mBookAdapter = BookAdapter()
        mBinding.rvBook.adapter = mBookAdapter
        mBinding.rvBook.layoutManager =GridLayoutManager(this,2)
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
            .putString("url",fileUrl)
            .putString("title",mTitle)
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

                            Toast.makeText(this, "Download is not successful", Toast.LENGTH_SHORT).show()
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

    override fun onTapPdfViewHolder(title: String, fileUrl: String) {
        val dialogBuilder = MaterialAlertDialogBuilder(this,R.style.RoundedAlertDialog)
            .setTitle(mTitle)
            .setMessage("Are you sure to Download?")
            .setPositiveButton("OK"){dialog,_ ->
                checkPermissionsAndDownload()
                dialog.dismiss()
            }
            .setNegativeButton("Cancel"){dialog,_->
                dialog.dismiss()
            }
        val alertDialog = dialogBuilder.create()
        alertDialog.show()
    }
}