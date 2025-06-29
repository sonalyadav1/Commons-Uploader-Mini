# Development Guide

## Getting Started

### Prerequisites
- Android Studio Arctic Fox or later
- JDK 8 or higher
- Android SDK 24+
- Git

### Quick Setup
1. Clone the repository
2. Copy `local.properties.template` to `local.properties` and update SDK path
3. Copy `google-services.json.template` to `google-services.json` and configure Firebase
4. Open project in Android Studio
5. Sync Gradle files
6. Run the app

## Architecture Overview

### MVVM Pattern
- **Model**: Data classes in `models/` package
- **View**: Composable screens in `ui/screens/`
- **ViewModel**: Business logic in `ui/viewmodels/`

### Key Components

#### Firebase Integration
- **Storage**: Image files stored in `/images/` bucket
- **Firestore**: Metadata stored in `uploads` collection
- **Configuration**: Set up via `google-services.json`

#### Offline Support
- **Queue Manager**: Local storage of failed uploads
- **WorkManager**: Background retry mechanism
- **Network Manager**: Connectivity monitoring

#### Image Handling
- **EXIF Utils**: Metadata extraction from images
- **Coil**: Async image loading and caching
- **FileProvider**: Secure camera image handling

## Development Workflow

### Adding New Features
1. Create feature branch: `git checkout -b feature/new-feature`
2. Implement in appropriate package
3. Add tests if applicable
4. Update documentation
5. Submit pull request

### Code Style
- Follow Kotlin coding conventions
- Use meaningful variable names
- Add comments for complex logic
- Format with Android Studio formatter

### Testing
```bash
# Run unit tests
./gradlew test

# Run instrumented tests
./gradlew connectedAndroidTest

# Generate test coverage report
./gradlew jacocoTestReport
```

## Build Variants

### Debug
- Debug logging enabled
- Test Firebase project
- Development security rules

### Release
- Minification enabled
- Production Firebase project
- Strict security rules
- Signed APK required

## Debugging Tips

### Common Issues
1. **Firebase not working**: Check `google-services.json` file
2. **Camera not working**: Verify permissions in manifest
3. **Upload failing**: Check network connectivity and Firebase rules
4. **Images not loading**: Verify Coil configuration

### Logging
Use `Log.d(TAG, message)` for debug information:
```kotlin
companion object {
    private const val TAG = "UploadManager"
}

Log.d(TAG, "Starting upload for file: ${upload.fileName}")
```

### Firebase Debugging
- Check Firebase Console for errors
- Enable debug logging: `adb shell setprop log.tag.FA VERBOSE`
- Use Firebase Emulator for local testing

## Performance Optimization

### Image Handling
- Compress images before upload
- Use appropriate image sizes
- Implement image caching

### Network
- Implement proper retry mechanisms
- Use background uploads for large files
- Cache network responses when possible

### UI
- Use `remember` for expensive calculations
- Implement lazy loading for lists
- Avoid unnecessary recompositions

## Security Considerations

### Firebase Rules
- Implement proper authentication
- Restrict read/write access
- Validate file types and sizes

### Local Storage
- Encrypt sensitive data
- Use secure file providers
- Clear temporary files

### Permissions
- Request minimal required permissions
- Handle permission denials gracefully
- Explain permission usage to users

## Deployment

### Pre-release Checklist
- [ ] All tests passing
- [ ] Firebase rules configured
- [ ] App signing configured
- [ ] Permissions reviewed
- [ ] Performance tested
- [ ] Security audit completed

### Release Process
1. Update version in `build.gradle`
2. Generate signed APK
3. Test on multiple devices
4. Deploy to Play Console
5. Monitor crash reports

## Monitoring

### Firebase Analytics
- Track upload success/failure rates
- Monitor user engagement
- Analyze feature usage

### Crash Reporting
- Firebase Crashlytics integration
- Monitor app stability
- Fix critical issues promptly

### Performance
- Track app startup time
- Monitor memory usage
- Optimize slow operations

## Contributing

### Code Review
- All changes require review
- Test thoroughly
- Follow style guidelines
- Update documentation

### Issue Reporting
- Use GitHub issue templates
- Provide reproduction steps
- Include device/OS information
- Attach relevant logs

## Resources

### Documentation
- [Firebase Documentation](https://firebase.google.com/docs)
- [Jetpack Compose Guide](https://developer.android.com/jetpack/compose)
- [Material Design 3](https://m3.material.io/)
- [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)

### Tools
- [Firebase Emulator](https://firebase.google.com/docs/emulator-suite)
- [Scrcpy](https://github.com/Genymobile/scrcpy) - Screen mirroring
- [Flipper](https://fbflipper.com/) - Debugging platform
