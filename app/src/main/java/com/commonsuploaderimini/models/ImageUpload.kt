package com.commonsuploaderimini.models

import java.util.Date

data class ImageUpload(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val language: String = "en",
    val fileName: String = "",
    val fileSize: Long = 0,
    val mimeType: String = "",
    val uploadDate: Date = Date(),
    val uploadStatus: UploadStatus = UploadStatus.PENDING,
    val storageUrl: String = "",
    val localPath: String = "",
    val thumbnailUrl: String = "",
    val exifData: Map<String, String> = emptyMap()
)

enum class UploadStatus {
    PENDING,
    UPLOADING,
    COMPLETED,
    FAILED,
    QUEUED
}

data class Language(
    val code: String,
    val name: String
) {
    companion object {
        val supportedLanguages = listOf(
            Language("en", "English"),
            Language("es", "Español"),
            Language("fr", "Français"),
            Language("de", "Deutsch"),
            Language("it", "Italiano"),
            Language("pt", "Português"),
            Language("ru", "Русский"),
            Language("ja", "日本語"),
            Language("ko", "한국어"),
            Language("zh", "中文"),
            Language("ar", "العربية"),
            Language("hi", "हिन्दी")
        )
    }
}
