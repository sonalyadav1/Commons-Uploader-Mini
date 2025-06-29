package com.commonsuploaderimini.utils

import android.content.Context
import android.net.Uri
import androidx.exifinterface.media.ExifInterface
import java.io.InputStream

object ExifUtils {
    fun extractExifData(context: Context, uri: Uri): Map<String, String> {
        val exifData = mutableMapOf<String, String>()
        
        try {
            val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
            inputStream?.let { stream ->
                val exifInterface = ExifInterface(stream)
                
                // Extract common EXIF tags
                exifInterface.getAttribute(ExifInterface.TAG_MAKE)?.let {
                    exifData["camera_make"] = it
                }
                
                exifInterface.getAttribute(ExifInterface.TAG_MODEL)?.let {
                    exifData["camera_model"] = it
                }
                
                exifInterface.getAttribute(ExifInterface.TAG_DATETIME)?.let {
                    exifData["date_taken"] = it
                }
                
                exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE)?.let { lat ->
                    exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE_REF)?.let { latRef ->
                        exifData["gps_latitude"] = "$lat $latRef"
                    }
                }
                
                exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE)?.let { lng ->
                    exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF)?.let { lngRef ->
                        exifData["gps_longitude"] = "$lng $lngRef"
                    }
                }
                
                exifInterface.getAttribute(ExifInterface.TAG_IMAGE_WIDTH)?.let {
                    exifData["image_width"] = it
                }
                
                exifInterface.getAttribute(ExifInterface.TAG_IMAGE_LENGTH)?.let {
                    exifData["image_height"] = it
                }
                
                exifInterface.getAttribute(ExifInterface.TAG_ORIENTATION)?.let {
                    exifData["orientation"] = it
                }
                
                stream.close()
            }
        } catch (e: Exception) {
            // Log error but don't fail the upload
            e.printStackTrace()
        }
        
        return exifData
    }
    
    fun generateTitleFromExif(exifData: Map<String, String>, fileName: String): String {
        // Try to generate a meaningful title from EXIF data
        val make = exifData["camera_make"]
        val model = exifData["camera_model"]
        val date = exifData["date_taken"]
        
        return when {
            make != null && model != null -> "Photo taken with $make $model"
            date != null -> "Photo from $date"
            else -> fileName.substringBeforeLast('.').replace('_', ' ').split(' ')
                .joinToString(" ") { it.capitalize() }
        }
    }
}
