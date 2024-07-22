# Dear Diary iOS App

Dear Diary is an iOS application that allows users to write, save, and analyze their notes and emotions. This app provides features such as getting note cover images, emotion analysis, and providing recommendations based on user inputs.

## Table of Contents

- [Features](#features)
- [Technologies Used](#technologies-used)
- [Installation](#installation)

## Features

- Write and save personal notes
- Get cover images for notes
- Analyze emotions in notes
- Receive recommendations based on emotions
- Beautiful UI with custom fonts and colors

## Technologies Used

### Languages and Frameworks

- **Swift**: The main programming language used for iOS development.
- **UIKit**: Used for building the user interface.
- **Core Data**: Used for local data persistence.
- **Alamofire**: Used for making network requests.
- **PinLayout**: Used for easy and readable UI layout.

### Other Tools

- **Xcode**: The main IDE used for iOS development.
- **Swift Package Manager (SPM)**: Dependency manager used to integrate Alamofire and PinLayout.

## Installation

To run this project on your local machine, follow these steps:

1. **Clone the Repository**

    ```bash
    git clone https://github.com/yourusername/dear-diary.git
    cd dear-diary
    ```

2. **Open the Project in Xcode**

    Open the `DearDiary.xcodeproj` file in Xcode:

    ```bash
    open DearDiary.xcodeproj
    ```

3. **Add Dependencies Using Swift Package Manager**

    - Go to your Xcode project settings.
    - Select the `DearDiary` project, then select the `DearDiary` target.
    - Click on the `+` button in the `Swift Packages` section.
    - Enter the URL of the Alamofire repository: `https://github.com/Alamofire/Alamofire.git`.
    - Choose the latest stable version of Alamofire and click `Add Package`.
  
    - Do the same steps for PinLayout (https://github.com/layoutBox/PinLayout).

4. **Set Up App Configuration**

    - Set up your Bundle Identifier in the Xcode project settings.
    - Ensure you have the necessary provisioning profiles and certificates for your development team.

5. **Build and Run the App**

    Select your target device or simulator and hit the `Run` button in Xcode.
