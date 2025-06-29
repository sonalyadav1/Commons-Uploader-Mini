<!-- Use this file to provide workspace-specific custom instructions to Copilot. For more details, visit https://code.visualstudio.com/docs/copilot/copilot-customization#_use-a-githubcopilotinstructionsmd-file -->

# Commons Uploader Mini - Android App

This is an Android Kotlin application that mimics Wikimedia Commons uploader functionality. The app allows users to upload images with metadata to Firebase.

## Key Technologies
- **Kotlin** - Primary programming language
- **Jetpack Compose** - Modern UI toolkit
- **Firebase Storage** - Image storage
- **Firebase Firestore** - Metadata storage
- **Material Design 3** - UI design system
- **MVVM Architecture** - Clean architecture pattern

## Project Structure
- `activities/` - Main activity and navigation
- `models/` - Data models (ImageUpload, Language, etc.)
- `upload/` - Upload management and Firebase integration
- `firestore/` - Firestore repository for metadata
- `utils/` - Utility classes (EXIF, offline queue)
- `ui/` - UI components, screens, and view models

## Key Features
- Image selection from gallery or camera
- Auto-title generation from EXIF data
- Multi-language support
- Offline queue for failed uploads
- Upload history and management
- Material Design 3 UI

## Code Style Guidelines
- Use Kotlin coroutines for async operations
- Follow MVVM pattern with StateFlow for reactive UI
- Use Jetpack Compose for all UI components
- Implement proper error handling and loading states
- Use dependency injection where appropriate

## Firebase Setup Required
- Configure Firebase project
- Add google-services.json file
- Enable Storage and Firestore
- Set up authentication (optional for MVP)
