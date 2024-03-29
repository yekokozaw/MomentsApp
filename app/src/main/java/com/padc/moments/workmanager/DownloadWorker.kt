package com.padc.moments.workmanager

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.util.UUID


class DownloadWorker(private val context : Context, workerParams : WorkerParameters)
    : Worker(context,workerParams) {
    override fun doWork(): Result {
        val url = inputData.getString("url")
        try {
            val request =
                DownloadManager.Request(Uri.parse(url))
            request.apply {
                setTitle("Download Image")
                setDescription("Downloading")
                setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,"${UUID.randomUUID()}.jpeg")

                val downloadManager : DownloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                downloadManager.enqueue(request)
            }

            return Result.success()

        }catch (e : Exception){
            return Result.failure()
        }
    }

}