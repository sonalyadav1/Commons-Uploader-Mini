package com.commonsuploaderimini.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.commonsuploaderimini.models.ImageUpload

class OfflineQueueManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("offline_queue", Context.MODE_PRIVATE)
    private val gson = Gson()
    private val queueKey = "pending_uploads"
    
    fun addToQueue(upload: ImageUpload) {
        val queue = getQueue().toMutableList()
        queue.add(upload)
        saveQueue(queue)
    }
    
    fun removeFromQueue(uploadId: String) {
        val queue = getQueue().toMutableList()
        queue.removeAll { it.id == uploadId }
        saveQueue(queue)
    }
    
    fun getQueue(): List<ImageUpload> {
        val json = prefs.getString(queueKey, "[]")
        val type = object : TypeToken<List<ImageUpload>>() {}.type
        return gson.fromJson(json, type) ?: emptyList()
    }
    
    fun clearQueue() {
        prefs.edit().remove(queueKey).apply()
    }
    
    private fun saveQueue(queue: List<ImageUpload>) {
        val json = gson.toJson(queue)
        prefs.edit().putString(queueKey, json).apply()
    }
    
    fun hasQueuedUploads(): Boolean {
        return getQueue().isNotEmpty()
    }
}
