




<h1 align="left">Weaver News App</h1>  

<p align="left">  
  <b>Stay Informed with the Global Latest News</b>  
</p>  
<p align="left">  
  <b>Built from a Concept</b><br>  
  Inspired by Rizal for Kretya Studio (<a href="https://dribbble.com/rzlartnto">profile</a>)<br></br>  

  <img src="https://cdn.dribbble.com/userupload/4987025/file/original-8039e9f39ace527b8e75f19223e6b303.png?resize=1504x1128" width="500" alt="Concept Image">  
</p>  

<p align="left">  
  <a href="#key-features">Key Features</a> •  
  <a href="#technologies">Technologies</a> •  
  <a href="#installation">Installation</a> •  
  <a href="#usage">Usage</a> •  
  <a href="#api-integration">API Integration</a> •  
  <a href="#screenshots">Screenshots</a> •  
  <a href="#contributing">Contributing</a> •  
  <a href="#license">License</a>  
</p>  

## Screenshots

<table>  
  <tr>  
    <td align="center">  
      <img src="https://raw.githubusercontent.com/mkaomwakuni/Weaver-News-App/139ff93742487ab051faeb6ce550f47e81c4af32/app/src/androidTest/java/dev/mkao/weaver/WhatsApp%20Image%202024-02-06%20at%207-portrait.png" width="510" alt="Screenshot 1">  
    </td>  
    <td align="center">  
      <img src="https://github.com/user-attachments/assets/95b413b8-9370-40fe-8283-e3b46b5295fc" width= "130" alt="Screenshot 2">  
    </td>  
        <td align="center">  
      <img src= https://github.com/user-attachments/assets/27657eda-a6f8-40ac-a7f0-08f6bad46680 width="400" alt="Screenshot 4">  
    </td>  
       <td align="center">  
      <img src="https://github.com/user-attachments/assets/c2e9d3e2-bb65-4e0d-bf5a-58edca0fc56f" width="400" alt="Screenshot 1">  
    </td>  
  </tr>  
  <tr>  
    <td align="center">  
      <img src="https://github.com/user-attachments/assets/06117615-d0a4-429e-8c74-0073037b3792" width="400" alt="Screenshot 3">  
    </td>  
       <td align="center">  
      <img src="https://github.com/user-attachments/assets/cbeded64-be2c-4eb1-b7b3-39cfd75e6b84" width="500" alt="Screenshot 1">  
    </td>  
    <td align="center">  
      <img src="https://github.com/user-attachments/assets/58e93298-0097-4951-b9f7-de0e7d012101 "width="400" alt="Screenshot 4">  
    </td>  
        <td align="center">  
      <img src="https://github.com/user-attachments/assets/b5344d42-5034-4ee2-bffa-dc73d778a9d5"  width="400" alt="Screenshot 5">  
    </td>  

  </tr>  
</table>  

## Key Features

- Browse and read the latest news articles from various trusted sources.
- Filter news by categories such as technology, sports, entertainment, and more.
- Save your favorite articles for offline reading (bookmarks).
- Home screen widget for quick access to the latest news.
- Push notifications for latest news updates.
- Multi-language support with customizable language preferences.
- Dark/Light theme support.
- Beautiful animations and transitions between screens.
- Loading effects for better user experience.
- Article sharing functionality.
- Detailed article view with HTML content parsing.
- Intuitive and user-friendly interface for a seamless news reading experience.

## Technologies

- <a href="https://github.com/JetBrains/kotlin">Kotlin</a>: Kotlin is a programming language that can run on JVM. Google has announced Kotlin as one of its officially supported programming languages in Android Studio; and the Android community is migrating at a pace from Java to Kotlin
- <a href="https://github.com/android/compose">Jetpack Compose</a>  – Android’s modern  declarative UI toolkit  for building dynamic and responsive interfaces with Kotlin.

-  <a href="https://developer.android.com/reference/android/content/BroadcastReceiver">BroadcastReceiver</a> – A mechanism for  listening to system-wide or app-specific events  (e.g., network changes, battery status, screen on/off). Unlike background tasks, it reacts to real-time events.

-   <a href="https://developer.android.com/reference/androidx/work/WorkManager">WorkManager</a>  – A flexible  background task scheduler  for deferrable work (e.g., syncing data, notifications). Ensures tasks run even if the app closes.
- <a href="https://github.com/square/retrofit">Retrofit</a>: Retrofit is a REST client for Java/ Kotlin and Android by Square inc under Apache 2.0 license. Its a simple network library that is used for network transactions. By using this library we can seamlessly capture JSON response from web service/web API
- <a href="https://github.com/coil-kt/coil">Coil</a>: Image loading library for Kotlin coroutines.
- <a href="https://github.com/android/architecture-components-samples/tree/main/RoomSample">  
  Room</a>: Local database for efficient data storage. 
- <a href ="https://material.io/develop/android"> Material </a>: Modular and customizable Material Design UI components for Android.  
- <a href="https://developer.android.com/develop/ui/views/appwidgets">App Widgets</a>: Home screen    widget for quick news access.
- <a href="https://developer.android.com/training/notify-user/build-notification">Notifications</a>:    Push notifications for latest news.
- <a href="https://developer.android.com/kotlin/flow">Kotlin Flow</a>: For asynchronous data    streaming.
- <a href="https://developer.android.com/kotlin/coroutines">Kotlin Coroutines</a>: A concurrency design pattern that you can use on Android to simplify code that executes asynchronously.
- <a href="https://developer.android.com/topic/libraries/architecture/viewmodel"> ViewModel </a>: The ViewModel class is designed to store and manage UI-related data in a lifecycle conscious way
 <a href="https://developer.android.com/guide/navigation/navigation-getting-started">  Navigation</a>: Helps you implement navigation, from simple button clicks to more complex patterns, such as app bars and the navigation drawer.  
- <a href="https://developer.android.com/develop/ui/views/launch/splash-screen">Hilt </a>: A dependency injection library for Android that reduces the boilerplate of doing manual dependency injection in your project


## MVVM Architecture

This project follows the MVVM (Model-View-ViewModel) architecture pattern for clean separation of  
concerns:

- **Model**: Represents data and business logic (domain models, repositories)
- **View**: UI components built with Jetpack Compose
- **ViewModel**: Manages UI-related data, handles user interactions, and communicates with  
  repositories


## Setup Requirements

-   Android device or emulator
-   Android Studio

## Getting Started

[](https://github.com/mkaomwakuni/Weaver-News-App)

In order to get the app running yourself, you need to:

1.  Clone this project
2.  Import the project into Android Studio
3.  Connect your android device with USB or just start your emulator
4.  After the project has finished setting up it stuffs, click the run button

## Support

[](https://github.com/mkaomwakuni/Weaver-News-App)

-   Found this project useful ❤️? Support by clicking the ⭐️ button on the upper right of this page. ✌️
-   Notice anything else missing? File an issue
-   Feel free to contribute in any way to the project from typos in docs to code review are all welcome.