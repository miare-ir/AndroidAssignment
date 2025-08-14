# ⚽ Football Players App

A modern Android application showcasing top football players from various leagues with advanced features and clean architecture.

## ✨ Features

- **📱 Player List**: Paginated display of football players with smooth scrolling (local data paging)
- **🔍 Smart Sorting**: Multiple sorting options (Alphabetical, Most Goals, Team & League Rank)
- **⭐ Follow System**: Follow/unfollow players with persistent local storage
- **📋 Dedicated Views**: Separate screens for all players and followed players
- **🎨 Modern UI**: Beautiful Material 3 design with Jetpack Compose

## 🏗️ Architecture

- **Clean Architecture** with clear separation of concerns
- **MVVM** pattern with StateFlow for reactive state management
- **Repository Pattern** for data abstraction
- **Use Cases** for business logic
- **Hilt** for dependency injection
- **Jetpack Paging 3** for efficient local data pagination

## 🛠️ Tech Stack

- **UI**: Jetpack Compose + Material 3
- **Architecture**: MVVM + Clean Architecture
- **State Management**: StateFlow + Coroutines
- **Data**: DataStore + Retrofit + MockFit
- **Testing**: JUnit + Compose UI Tests + Hilt Testing
- **Dependency Injection**: Hilt

## 🚀 Quick Start

1. **Clone** the repository
2. **Open** in Android Studio
3. **Build** the project (generates intermediate classes)
4. **Run** on device/emulator

## 📱 App Structure

- **Main Screen**: All players with sorting and pagination
- **Followed Tab**: Personal collection of followed players
- **Sort Options**: A-Z, Most Goals, Team & League Rank
- **Follow Toggle**: Star icon for each player

## 🧪 Testing

- **Unit Tests**: ViewModel, Use Cases, Repositories, Paging
- **UI Tests**: Navigation, Sorting, Player Display
- **Test Coverage**: Comprehensive testing of key components

## 📊 Data Source

- **Local Paging System**: Implemented local paging since network pagination wasn't available
- All data loaded from bundled JSON assets for smooth offline experience
- Mock API with local JSON data for development/testing
- Football players from top leagues (La Liga, Premier League, etc.)
- Player stats, team rankings, and league information

---

*Built with modern Android development practices and best practices for scalability and maintainability.*

