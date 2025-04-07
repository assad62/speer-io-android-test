# GitHub User Search App

A modern Android application built with Kotlin and Jetpack Compose that allows users to search for GitHub users, view their profiles, and explore their followers and following lists.

## Features

- 🔍 Search for GitHub users
- 👤 View detailed user profiles
- 👥 Browse user followers
- 🤝 Explore who users are following
- 🎨 Modern Material Design UI
- 🌐 Real-time GitHub API integration

## Prerequisites

Before you begin, ensure you have the following installed:

- [Android Studio](https://developer.android.com/studio) (latest version)
- [Android SDK](https://developer.android.com/studio/releases/platforms)
- [Git](https://git-scm.com/downloads)
- A GitHub account (for API access)

## Setup Instructions

1. **Clone the Repository**
   ```bash
   git clone [repository-url]
   cd GithubUserSearchApp
   ```

2. **Open the Project**
   - Launch Android Studio
   - Select "Open an existing project"
   - Navigate to the cloned repository and select it
   - Wait for the Gradle sync to complete

3. **Configure GitHub API Access**
   - Go to your GitHub account settings
   - Navigate to Developer Settings > Personal Access Tokens
   - Generate a new token with the following permissions:
     - `read:user`
     - `user:follow`
   - Copy the generated token

4. **Add API Token**
   - Open `app/src/main/java/com/mohammadassad/githubusersearchapp/di/NetworkModule.kt`
   - Replace the placeholder token in the `GithubApiService` with your actual token:
   ```kotlin
   .addHeader("Authorization", "token YOUR_TOKEN_HERE")
   ```

5. **Build and Run**
   - Connect an Android device or start an emulator
   - Click the "Run" button in Android Studio (green play icon)
   - Select your target device
   - Wait for the app to build and install

## Usage Guide

1. **Searching for Users**
   - Launch the app
   - Use the search bar at the top to enter a GitHub username
   - Results will appear in real-time as you type

2. **Viewing User Profiles**
   - Tap on any user from the search results
   - View their profile information including:
     - Profile picture
     - Username
     - Bio
     - Followers count
     - Following count

3. **Exploring Connections**
   - On a user's profile, tap:
     - "Followers" to see who follows them
     - "Following" to see who they follow
   - Tap on any user in these lists to view their profile

4. **Navigation**
   - Use the back button to return to previous screens
   - The app maintains a navigation stack for easy backtracking

## Technical Stack

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Architecture**: Clean Architecture
- **Dependency Injection**: Dagger Hilt
- **Networking**: Retrofit
- **Asynchronous Programming**: Kotlin Coroutines
- **State Management**: StateFlow
- **Image Loading**: Coil

## Project Structure

```
app/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/mohammadassad/githubusersearchapp/
│   │   │       ├── di/           # Dependency injection modules
│   │   │       ├── domain/       # Business logic and use cases
│   │   │       ├── data/         # Data layer and repositories
│   │   │       ├── presentation/ # UI components and ViewModels
│   │   │       └── navigation/   # Navigation components
│   │   └── res/                  # Resources (drawables, strings, etc.)
```

## Troubleshooting

1. **Build Errors**
   - Ensure all prerequisites are installed
   - Try "File > Invalidate Caches / Restart"
   - Check for any missing dependencies in build.gradle

2. **API Issues**
   - Verify your GitHub token is valid
   - Check your internet connection
   - Ensure the token has the required permissions

3. **Runtime Errors**
   - Check the logcat for detailed error messages
   - Ensure you're using a compatible Android version (API 24+)

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Support

For support, please open an issue in the GitHub repository or contact the maintainers. 