package com.commonsuploaderimini.upload

import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.google.firebase.ktx.Firebase
import com.commonsuploaderimini.models.ImageUpload
import com.commonsuploaderimini.models.UploadStatus
import com.commonsuploaderimini.firestore.FirestoreRepository
import kotlinx.coroutines.tasks.await
import java.io.File
import java.util.*

class UploadManager(private val context: Context) {
    private val storage: FirebaseStorage = Firebase.storage
    private val firestoreRepository = FirestoreRepository()
    
    suspend fun uploadImage(
        uri: Uri,
        title: String,
        description: String,
        language: String,
        exifData: Map<String, String> = emptyMap()
    ): Result<ImageUpload> {
        return try {
            val fileName = generateFileName()
            val mimeType = getMimeType(uri) ?: "image/jpeg"
            val fileSize = getFileSize(uri)
            
            // Create initial upload record
            val initialUpload = ImageUpload(
                title = title,
                description = description,
                language = language,
                fileName = fileName,
                fileSize = fileSize,
                mimeType = mimeType,
                uploadStatus = UploadStatus.UPLOADING,
                localPath = uri.toString(),
                exifData = exifData
            )
            
            // Save to Firestore first
            val saveResult = firestoreRepository.saveImageUpload(initialUpload)
            if (saveResult.isFailure) {
                return Result.failure(saveResult.exceptionOrNull()!!)
            }
            
            val uploadId = saveResult.getOrThrow()
            val uploadWithId = initialUpload.copy(id = uploadId)
            
            // Upload to Firebase Storage
            val storageRef = storage.reference.child("images/$fileName")
            val uploadTask = storageRef.putFile(uri).await()
            val downloadUrl = storageRef.downloadUrl.await()
            
            // Update with final details
            val completedUpload = uploadWithId.copy(
                storageUrl = downloadUrl.toString(),
                uploadStatus = UploadStatus.COMPLETED
            )
            
            firestoreRepository.updateImageUpload(completedUpload)
            Result.success(completedUpload)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    private fun generateFileName(): String {
        val timestamp = System.currentTimeMillis()
        val random = UUID.randomUUID().toString().take(8)
        return "upload_${timestamp}_$random.jpg"
    }
    
    private fun getMimeType(uri: Uri): String? {
        val contentResolver = context.contentResolver
        return contentResolver.getType(uri)
    }
    
    private fun getFileSize(uri: Uri): Long {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri)
            inputStream?.available()?.toLong() ?: 0L
        } catch (e: Exception) {
            0L
        }
    }
}
