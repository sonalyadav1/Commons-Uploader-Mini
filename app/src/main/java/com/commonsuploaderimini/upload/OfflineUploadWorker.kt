package com.commonsuploaderimini.upload

import android.content.Context
import androidx.work.*
import com.commonsuploaderimini.firestore.FirestoreRepository
import com.commonsuploaderimini.models.ImageUpload
import com.commonsuploaderimini.models.UploadStatus
import com.commonsuploaderimini.utils.OfflineQueueManager
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.util.concurrent.TimeUnit

class OfflineUploadWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {
    
    private val storage: FirebaseStorage = Firebase.storage
    private val firestoreRepository = FirestoreRepository()
    private val offlineQueueManager = OfflineQueueManager(context)
    
    override suspend fun doWork(): Result {
        return try {
            val queuedUploads = offlineQueueManager.getQueue()
            
            for (upload in queuedUploads) {
                try {
                    // Update status to uploading
                    val updatedUpload = upload.copy(uploadStatus = UploadStatus.UPLOADING)
                    firestoreRepository.updateImageUpload(updatedUpload)
                    
                    // Upload to Firebase Storage if not already uploaded
                    if (upload.storageUrl.isEmpty()) {
                        val storageRef = storage.reference.child("images/${upload.fileName}")
                        val uploadTask = storageRef.putFile(android.net.Uri.parse(upload.localPath)).await()
                        val downloadUrl = storageRef.downloadUrl.await()
                        
                        // Update with final details
                        val completedUpload = updatedUpload.copy(
                            storageUrl = downloadUrl.toString(),
                            uploadStatus = UploadStatus.COMPLETED
                        )
                        
                        firestoreRepository.updateImageUpload(completedUpload)
                    }
                    
                    // Remove from offline queue
                    offlineQueueManager.removeFromQueue(upload.id)
                    
                } catch (e: Exception) {
                    // Mark as failed
                    val failedUpload = upload.copy(uploadStatus = UploadStatus.FAILED)
                    firestoreRepository.updateImageUpload(failedUpload)
                }
            }
            
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
    
    companion object {
        private const val WORK_NAME = "offline_upload_work"
        
        fun enqueue(context: Context) {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()
            
            val workRequest = OneTimeWorkRequestBuilder<OfflineUploadWorker>()
                .setConstraints(constraints)
                .setBackoffCriteria(
                    BackoffPolicy.EXPONENTIAL,
                    OneTimeWorkRequest.MIN_BACKOFF_MILLIS,
                    TimeUnit.MILLISECONDS
                )
                .build()
            
            WorkManager.getInstance(context)
                .enqueueUniqueWork(WORK_NAME, ExistingWorkPolicy.REPLACE, workRequest)
        }
    }
}
