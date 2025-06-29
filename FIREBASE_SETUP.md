# Firebase Configuration Guide

## Setting up Firebase for Commons Uploader Mini

### Step 1: Create a Firebase Project
1. Go to [Firebase Console](https://console.firebase.google.com)
2. Click "Create a project" or "Add project"
3. Enter project name: `commons-uploader-mini`
4. Choose your analytics preferences
5. Click "Create project"

### Step 2: Add Android App
1. In your Firebase project, click "Add app" and select Android
2. Enter the package name: `com.commonsuploaderimini`
3. Enter app nickname: `Commons Uploader Mini`
4. Download the `google-services.json` file
5. Place it in the `app/` directory of your project

### Step 3: Enable Firebase Services

#### Firebase Storage
1. In Firebase Console, go to "Storage"
2. Click "Get started"
3. Choose "Start in test mode" for development
4. Select a location for your storage bucket

#### Firebase Firestore
1. In Firebase Console, go to "Firestore Database"
2. Click "Create database"
3. Choose "Start in test mode" for development
4. Select a location for your database

### Step 4: Configure Security Rules

#### Storage Rules
```javascript
rules_version = '2';
service firebase.storage {
  match /b/{bucket}/o {
    match /images/{imageId} {
      allow read, write: if true; // For development - adjust for production
    }
  }
}
```

#### Firestore Rules
```javascript
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    match /uploads/{document} {
      allow read, write: if true; // For development - adjust for production
    }
  }
}
```

### Step 5: Production Security (Optional)
For production apps, consider implementing authentication and more restrictive rules:

```javascript
// Storage rules with auth
rules_version = '2';
service firebase.storage {
  match /b/{bucket}/o {
    match /images/{userId}/{imageId} {
      allow read, write: if request.auth != null && request.auth.uid == userId;
    }
  }
}

// Firestore rules with auth
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    match /uploads/{userId}/images/{document} {
      allow read, write: if request.auth != null && request.auth.uid == userId;
    }
  }
}
```

### Step 6: Test Your Setup
1. Build and run the app
2. Try uploading an image
3. Check Firebase Console to see uploaded files and metadata

### Troubleshooting
- Make sure `google-services.json` is in the `app/` directory
- Verify package name matches exactly
- Check internet connectivity
- Ensure Storage and Firestore are enabled
- Check Firebase Console for error logs
