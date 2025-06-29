package com.commonsuploaderimini.firestore

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.commonsuploaderimini.models.ImageUpload
import kotlinx.coroutines.tasks.await

class FirestoreRepository {
    private val db: FirebaseFirestore = Firebase.firestore
    private val uploadsCollection = "uploads"

    suspend fun saveImageUpload(upload: ImageUpload): Result<String> {
        return try {
            val docRef = db.collection(uploadsCollection).document()
            val uploadWithId = upload.copy(id = docRef.id)
            docRef.set(uploadWithId).await()
            Result.success(docRef.id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateImageUpload(upload: ImageUpload): Result<Unit> {
        return try {
            db.collection(uploadsCollection)
                .document(upload.id)
                .set(upload)
                .await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getImageUploads(): Result<List<ImageUpload>> {
        return try {
            val snapshot = db.collection(uploadsCollection)
                .orderBy("uploadDate", com.google.firebase.firestore.Query.Direction.DESCENDING)
                .get()
                .await()
            
            val uploads = snapshot.documents.mapNotNull { doc ->
                doc.toObject(ImageUpload::class.java)
            }
            Result.success(uploads)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteImageUpload(uploadId: String): Result<Unit> {
        return try {
            db.collection(uploadsCollection)
                .document(uploadId)
                .delete()
                .await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
