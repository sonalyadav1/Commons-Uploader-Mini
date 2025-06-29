# Commons Uploader Mini

A simplified Android version of Wikimedia Commons uploader where users can upload photos with titles and descriptions.

## 🚀 Features

- **Image Selection**: Choose photos from gallery or camera using ActivityResultContracts
- **Rich Metadata**: Add titles, descriptions, and language information
- **Firebase Integration**: Secure upload to Firebase Storage with metadata in Firestore
- **Modern UI**: Material Design 3 with Jetpack Compose
- **Auto-fill**: Automatic title generation from image name or EXIF data
- **Upload History**: View and manage previously uploaded images
- **Offline Support**: Queue uploads when offline and retry later
- **Multi-language**: Support for 12+ languages

## 🛠 Tech Stack

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose with Material Design 3
- **Architecture**: MVVM with StateFlow
- **Backend**: Firebase (Storage + Firestore)
- **Image Handling**: Coil for image loading, ExifInterface for metadata
- **Permissions**: Accompanist Permissions
- **Navigation**: Navigation Compose

## 📁 Project Structure

```
/app
 ├── /activities          # MainActivity with navigation
 ├── /models             # Data models (ImageUpload, Language)
 ├── /upload             # UploadManager for Firebase operations
 ├── /firestore          # FirestoreRepository for metadata
 ├── /utils              # ExifUtils, OfflineQueueManager
 └── /ui
     ├── /screens        # HomeScreen, UploadScreen, MyUploadsScreen
     ├── /viewmodels     # UploadViewModel
     └── /theme          # Material Design theme
```

## 🔧 Setup Instructions

### Prerequisites
- Android Studio (latest version)
- Android SDK 24+
- Firebase project

### Firebase Setup
1. Create a new Firebase project at https://console.firebase.google.com
2. Add an Android app to your project
   - Package name: `com.commonsuploaderimini`
3. Download `google-services.json` and place it in `app/` directory
4. Enable Firebase Storage and Firestore in the Firebase console
5. Set up Storage security rules:
   ```javascript
   rules_version = '2';
   service firebase.storage {
     match /b/{bucket}/o {
       match /images/{imageId} {
         allow read, write: if true; // Adjust based on your auth requirements
       }
     }
   }
   ```
6. Set up Firestore security rules:
   ```javascript
   rules_version = '2';
   service cloud.firestore {
     match /databases/{database}/documents {
       match /uploads/{document} {
         allow read, write: if true; // Adjust based on your auth requirements
       }
     }
   }
   ```

### Build and Run
1. Clone this repository
2. Open in Android Studio
3. Add your `google-services.json` file
4. Sync project with Gradle files
5. Run on device or emulator

## 📱 App Usage

1. **Home Screen**: Welcome screen with navigation options
2. **Upload Screen**: 
   - Select image from gallery or camera
   - Auto-generated title from EXIF data
   - Add description and select language
   - Upload to Firebase
3. **My Uploads**: View upload history and manage images

## 🎨 UI Screenshots

The app features a clean, modern Material Design 3 interface with:
- Intuitive navigation
- Responsive layouts
- Beautiful image previews
- Status indicators for uploads
- Error handling with user-friendly messages

## 🔐 Permissions

The app requires the following permissions:
- `READ_EXTERNAL_STORAGE` / `READ_MEDIA_IMAGES` - For gallery access
- `CAMERA` - For camera functionality
- `INTERNET` - For Firebase uploads
- `ACCESS_NETWORK_STATE` - For network status

## 🌟 Bonus Features

- **EXIF Data Extraction**: Automatically extracts camera information, GPS data, and image properties
- **Auto-title Generation**: Intelligent title generation from EXIF data or filename
- **Offline Queue**: Stores failed uploads locally and retries when connection is restored
- **Multi-language Support**: Interface supports 12+ languages
- **Upload Status Tracking**: Real-time status updates for upload progress

## 🚀 Future Enhancements

- User authentication
- Image editing capabilities
- Batch upload support
- Social sharing features
- Advanced search and filtering
- Cloud sync across devices

## 📄 License

This project is open source and available under the [MIT License](LICENSE).

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## 📞 Support

For questions or issues, please open an issue on this repository.
