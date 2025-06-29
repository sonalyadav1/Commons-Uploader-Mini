package com.commonsuploaderimini.activities

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.commonsuploaderimini.ui.theme.CommonsUploaderMiniTheme
import com.commonsuploaderimini.ui.screens.HomeScreen
import com.commonsuploaderimini.ui.screens.UploadScreen
import com.commonsuploaderimini.ui.screens.MyUploadsScreen
import com.commonsuploaderimini.ui.viewmodels.UploadViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CommonsUploaderMiniTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainContent()
                }
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MainContent() {
    val navController = rememberNavController()
    val uploadViewModel: UploadViewModel = viewModel()
    
    // Permission handling
    val permissionsState = rememberMultiplePermissionsState(
        listOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_MEDIA_IMAGES,
            Manifest.permission.CAMERA
        )
    )
    
    Scaffold { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(paddingValues)
        ) {
            composable("home") {
                HomeScreen(
                    navController = navController,
                    permissionsState = permissionsState
                )
            }
            composable("upload") {
                UploadScreen(
                    navController = navController,
                    uploadViewModel = uploadViewModel,
                    permissionsState = permissionsState
                )
            }
            composable("my_uploads") {
                MyUploadsScreen(
                    navController = navController,
                    uploadViewModel = uploadViewModel
                )
            }
        }
    }
}
