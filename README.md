# MD3DemoApp - Material Design 3 Demo

A complete Material Design 3 demo application built with Jetpack Compose, showcasing modern Android development patterns and Material 3 components.

## Features

### 🎨 Material Design 3
- Complete Material 3 theme system with light/dark modes
- All major MD3 components implemented
- Proper color tokens and typography scales
- 8dp baseline grid system
- Accessible design with proper contrast ratios

### 🏗️ Architecture
- Clean Architecture with MVVM pattern
- Repository pattern for data management
- Flow-based reactive programming
- State management with StateFlow/Compose State

### 🌐 Networking & Data
- Ktor client for HTTP networking
- Kotlinx.serialization for JSON parsing
- Integration with public APIs:
  - **FakeStore API**: Product catalog and details
  - **JSONPlaceholder**: User profiles and posts
  - **GitHub API**: User search functionality

### 📱 Screens Implemented
- **Home Screen**: Product catalog with search functionality
- **Cart Screen**: Shopping cart with quantity management
- **Profile Screen**: User profile display (placeholder)
- **Settings Screen**: App settings and preferences (placeholder)
- **Product Detail Screen**: Detailed product view (placeholder)

## 🚀 Getting Started

### Prerequisites
- Android Studio Hedgehog (2023.1.1) or later
- JDK 17 or later
- Android SDK with API level 34

### Build Instructions

1. **Clone the repository**
   ```bash
   git clone [repository-url]
   cd app-exemplo
   ```

2. **Build and Run Android App**
   ```bash
   # Build debug APK
   ./gradlew :androidApp:assembleDebug
   
   # Install on connected device/emulator
   ./gradlew :androidApp:installDebug
   ```

3. **Run Tests**
   ```bash
   # Run unit tests
   ./gradlew :androidApp:testDebug
   
   # Run instrumentation tests
   ./gradlew :androidApp:connectedAndroidTest
   ```

### 📱 Development Setup
- Ensure you have an Android device or emulator running API 24+
- The app requires internet connectivity to load product data
- For optimal experience, use Android 13+ devices for full Material You support

## 🧩 Material 3 Components Showcase

| Screen | Components Used |
|--------|----------------|
| **Home Screen** | `SearchBar`, `LazyColumn`, `Card`, `CircularProgressIndicator`, `Button`, `Icon`, `Text` |
| **Cart Screen** | `Card`, `LazyColumn`, `FilledIconButton`, `NavigationBar`, `Button` |
| **Navigation** | `NavigationBar`, `NavigationBarItem`, `Scaffold` |
| **Common** | `Surface`, `MaterialTheme`, `Typography`, `ColorScheme` |

### API Endpoints Used
- **Products**: `https://fakestoreapi.com/products`
- **Product Detail**: `https://fakestoreapi.com/products/{id}`
- **Users**: `https://jsonplaceholder.typicode.com/users`
- **Search**: `https://api.github.com/search/users`

## 🔧 Technical Stack

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Architecture**: MVVM + Clean Architecture
- **Networking**: Ktor Client
- **Serialization**: kotlinx.serialization
- **Navigation**: Compose Navigation
- **Image Loading**: Coil
- **State Management**: StateFlow + Compose State
- **Dependency Injection**: Manual (for simplicity)

## 📋 Project Structure

```
androidApp/
├── src/main/kotlin/com/md3demo/
│   ├── data/
│   │   ├── model/          # Data models (Product, User, Cart)
│   │   ├── network/        # API service and HTTP client
│   │   └── repository/     # Data repositories and cart management
│   ├── ui/
│   │   ├── components/     # Reusable UI components
│   │   ├── navigation/     # Navigation routes
│   │   ├── screens/        # Screen composables
│   │   ├── theme/          # Material 3 theme system
│   │   └── viewmodel/      # ViewModels for state management
│   ├── util/              # Utility classes and result wrappers
│   └── MainActivity.kt    # Main activity entry point
├── src/main/res/
│   ├── drawable/          # Vector icons
│   ├── values/            # Strings, colors, themes
│   └── mipmap*/           # App launcher icons
└── build.gradle.kts       # Module build configuration
```

## 🎯 Development Status

### ✅ Completed
- Project structure and build configuration
- Complete data layer with networking
- Material 3 theme system
- Home screen with product catalog
- Cart functionality with local state management
- Basic navigation between screens
- Error handling and loading states

### 🚧 In Progress / Future Enhancements
- Complete Material 3 components showcase
- Profile screen with user data
- Advanced search functionality
- Settings screen with theme toggle
- Product detail screen with image carousel
- Unit and integration tests
- Web support with Compose Multiplatform
- CI/CD pipeline

## 🐛 Known Issues
- Network connectivity required for API data
- Some placeholder screens need full implementation
- Limited offline functionality

## 📄 License
This project is for educational and demonstration purposes.

---
**Built with ❤️ using Material Design 3 and Jetpack Compose**