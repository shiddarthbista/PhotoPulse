# <h1 align="center"> :camera: PhotoPulse :camera: </h1>

## Table of Contents

1.  [Overview](#overview)
2.  [Screenshots](#screenshots)
3.  [Features](#features)
4.  [Technologies Used](#technologies-used)
5.  [Prerequisites](#prerequisites)
6.  [Running the App](#how-to-run-the-app-locally)
7.  [Status](#status)
8.  [Further Development](#further-development)


## Overview

PhotoPulse is an Android application that allows users to capture, view, and share photos with ease. The app provides a streamlined experience for taking pictures using the device's camera, selecting images from the gallery, and then sharing those photos with friends and family through various social media platforms. The app is built using Kotlin and Jetpack Compose, following modern Android development practices.

## Screenshots
<img src="https://github.com/user-attachments/assets/403ec4c4-3e35-4b10-a897-37cc3cbacd31" width="150" height="280">
<img src="https://github.com/user-attachments/assets/ec179c85-742b-4066-b522-33bc32fc6ca0" width="150" height="280">
<img src="https://github.com/user-attachments/assets/9b81ec11-c4c9-47ab-ad0c-8d695ec23757" width="150" height="280">
<img src="https://github.com/user-attachments/assets/1bb89861-9858-482d-aa6e-9c0124c49872" width="150" height="280">

## Features
*   **View Photos:** Can scroll through infinite photos uploaded by other users of the app.
*   **Capture Photos:** Take pictures directly within the app using the device's camera.
*   **Gallery Integration:** Select existing photos from the device's gallery.
*   **Share Photos:** Easily share photos with other apps (e.g., social media, messaging).
*   **Modern UI:** A clean and intuitive user interface built with Jetpack Compose.
*   **Bottom navigation bar:** the user can navigate between screens with a bottom navigation bar.

## Technologies Used
[![Kotlin](https://img.shields.io/badge/Kotlin-%237F52FF.svg?logo=kotlin&logoColor=white)](#)
[![Android](https://img.shields.io/badge/Android-3DDC84?logo=android&logoColor=white)](#)

*   **Kotlin:** The primary programming language for Android development.
*   **Jetpack Compose:** A modern declarative UI toolkit for building Android interfaces.
*   **Material Design 3:** For creating a consistent and visually appealing UI.

## Prerequisites

*   **Android Studio:** Make sure you have the latest stable version of Android Studio installed. You can download it from [https://developer.android.com/studio](https://developer.android.com/studio).
*   **Android SDK:** Ensure that you have the necessary Android SDK components and build tools installed via the Android Studio SDK Manager. or
*   **Android Phone:** Android Phone works too
*   **Git:** To clone the project, you'll need Git installed.
*   **Emulator or device:** Make sure you have a emulator or a physical device to test the app.

## How to Run the App Locally

1.  **Clone the Repository:**
```
git clone https://github.com/shiddarthbista/PhotoPulse.git
```

2.  **Open in Android Studio:**

    *   Launch Android Studio.
    *   Click on "Open," and select the `PhotoPulse` directory that you just cloned.

3.  **Sync Project with Gradle Files:**

    *   Android Studio will automatically prompt you to sync the project with the Gradle files.
    *   If it doesn't, you can do it manually by clicking "File" > "Sync Project with Gradle Files."
    * This will download all the dependencies.

4.  **Create an Emulator or Connect a Device:**

    *   If you don't already have an Android Virtual Device (AVD) set up:
        *   Click on the AVD Manager icon (it looks like a phone with the Android logo).
        *   Follow the prompts to create a new virtual device. Choose a device configuration and an appropriate system image (API level).
    *   Alternatively, connect a physical Android device to your computer. Ensure that USB debugging is enabled on the device (in developer options).
    *   **QR Code:**
        *   Open the setting menu in the phone and search for `Pair device with QR code`.
        *   In Android Studio Select "Pair device using Wifi."
        *   Android Studio generate a QR code, which the phone can scan using its camera.
        *   Once scanned, the devices will be paired.
    *   **Pairing Number:**
        *   Open the connection menu in the app.
        *   Select "Connect via Pairing Number."
        *   Android Studio will generate a unique pairing number.
        *   Enter that number on your Android Phone.
        *   The devices will then be paired.

5.  **Run the App:**

    *   Click the "Run" button (the green play icon) in Android Studio.
    *   Select your emulator or connected device from the list.
    *   The app will be built and launched on the selected device.
  
    6. **Download APK and use on Android Device:**
         * Download the APK from [here](https://github.com/shiddarthbista/PhotoPulse/tree/master/app/src/main/java/bista/shiddarth/photopulse/apk)
         * Run on your Android Device
      

## Status
 🚧 In progress 🚧

PhotoPulse is still under development and some screens are not yet implemented.

- [ ] Comment Feature
- [x] Sharing Feature
- [ ] Add filter to Image
- [ ] Search for User

## Further Development

*   **Image Cropping/Editing:** Add basic image editing features.
* **Share multiple images**: allow the user to share multiple images at the same time.
*   **Cloud Storage:** Integrate with cloud storage services (like Firebase Storage) to allow users to back up their photos.
*   **Social Features:** Add social networking elements where users can follow each other and share photos within the app.
* **Improved Gallery:** make the gallery more usable.
*   **Refactor:** As the app grows, you might want to refactor code to improve maintainability.
*   **Testing:** Write unit and UI tests to make sure the app is working as expected.
