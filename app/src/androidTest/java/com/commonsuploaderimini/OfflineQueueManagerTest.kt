package com.commonsuploaderimini

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.commonsuploaderimini.models.ImageUpload
import com.commonsuploaderimini.models.Language
import com.commonsuploaderimini.models.UploadStatus
import com.commonsuploaderimini.utils.OfflineQueueManager
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

@RunWith(AndroidJUnit4::class)
class OfflineQueueManagerTest {
    
    private lateinit var offlineQueueManager: OfflineQueueManager
    private lateinit var testUpload: ImageUpload
    
    @Before
    fun setup() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        offlineQueueManager = OfflineQueueManager(context)
        
        testUpload = ImageUpload(
            id = "test-id-123",
            title = "Test Image",
            description = "This is a test upload",
            language = "en",
            fileName = "test_image.jpg",
            uploadStatus = UploadStatus.QUEUED,
            localPath = "content://test/path"
        )
        
        // Clear queue before tests
        offlineQueueManager.clearQueue()
    }
    
    @Test
    fun testAddToQueue() {
        // Test adding item to queue
        offlineQueueManager.addToQueue(testUpload)
        
        val queue = offlineQueueManager.getQueue()
        assertEquals(1, queue.size)
        assertEquals(testUpload.id, queue[0].id)
        assertEquals(testUpload.title, queue[0].title)
    }
    
    @Test
    fun testRemoveFromQueue() {
        // Add item first
        offlineQueueManager.addToQueue(testUpload)
        assertEquals(1, offlineQueueManager.getQueue().size)
        
        // Remove item
        offlineQueueManager.removeFromQueue(testUpload.id)
        assertEquals(0, offlineQueueManager.getQueue().size)
    }
    
    @Test
    fun testHasQueuedUploads() {
        // Initially should be empty
        assertFalse(offlineQueueManager.hasQueuedUploads())
        
        // Add item
        offlineQueueManager.addToQueue(testUpload)
        assertTrue(offlineQueueManager.hasQueuedUploads())
        
        // Clear queue
        offlineQueueManager.clearQueue()
        assertFalse(offlineQueueManager.hasQueuedUploads())
    }
    
    @Test
    fun testLanguageSupport() {
        val supportedLanguages = Language.supportedLanguages
        
        assertTrue(supportedLanguages.isNotEmpty())
        assertTrue(supportedLanguages.any { it.code == "en" })
        assertTrue(supportedLanguages.any { it.code == "es" })
        assertTrue(supportedLanguages.any { it.code == "fr" })
        
        // Check that each language has both code and name
        supportedLanguages.forEach { language ->
            assertFalse(language.code.isEmpty())
            assertFalse(language.name.isEmpty())
        }
    }
    
    @Test
    fun testImageUploadModel() {
        val upload = ImageUpload(
            title = "Sample Photo",
            description = "Beautiful landscape",
            language = "en",
            fileName = "landscape.jpg",
            uploadStatus = UploadStatus.COMPLETED
        )
        
        assertEquals("Sample Photo", upload.title)
        assertEquals("Beautiful landscape", upload.description)
        assertEquals("en", upload.language)
        assertEquals("landscape.jpg", upload.fileName)
        assertEquals(UploadStatus.COMPLETED, upload.uploadStatus)
        assertNotNull(upload.uploadDate)
    }
}
