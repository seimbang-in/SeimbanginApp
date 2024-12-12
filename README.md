# Seimbangin Mobile Apps

## Project Overview
This Android project integrates various advanced features, including OCR (Optical Character Recognition), AI advisor capabilities powered by a cloud-based project API's model, and a comprehensive transaction management system. The project also includes a chatbot for AI-driven suggestions with Gemini model, authentication mechanisms, and an attractive UI design aligned with the project's wireframe.

## Features
- **OCR (Optical Character Recognition):** Extracts text from images and processes it for various use cases.
- **AI Advisor:** Provides intelligent suggestions using the project API's.
- **Authentication:** Secure user authentication to protect data and ensure privacy.
- **Transaction Integration:** Allows users to manage transactions seamlessly with API support.
- **Chatbot:** Offers AI-driven interactions and suggestions with Gemini.
- **UI/UX:** A visually appealing and user-friendly interface that aligns with the project's wireframe.

## Technologies Used
- **Programming Language:** Kotlin
- **Frameworks & Libraries:**
  - Android Jetpack (ViewModel, LiveData, Navigation, etc.)
  - ViewBinding
  - Koin for Dependency Injection
  - Coil for image loading
  - Retrofit & ChuckerInterceptor
  - Lottie animation
  - UCrop & Android Image Cropper
  - etc.
- **API Services:** Project API for transaction handling and Gemini API for AI advisor.
- **Database:** Local database for transaction storage.
- **Tools:**
  - Android Studio

## Installation
1. Clone the repository:
    ```bash
    git clone https://github.com/seimbang-in/SeimbanginApp.git
    ```
2. Open the project in Android Studio.
3. Sync the project with Gradle files.
4. Configure the API keys and endpoints in the `gradle.properties` file or a secure configuration file:
    ```properties
    GEMINI_API_KEY=your_gemini_api_key
    ```
5. Run the project on an emulator or a physical device.

## Usage
1. Launch the app on your Android device.
2. Log in or register using the authentication module.
3. Access various features like:
   - Uploading images for OCR processing.
   - Interacting with the chatbot for AI-driven suggestions.
   - Managing financial transactions.
4. Explore the intuitive UI for a seamless user experience.

## Project Structure
```
app/
├── data/        # Contains data sources like local database and API services
├── di/          # Dependency injection modules
├── ui/          # User interface components
│   ├── auth/    # Authentication screens
│   ├── chatbot/ # Chatbot-related screens and logic
│   ├── ocr/     # OCR processing UI
│   ├── transaction/ # Transaction management screens
│   └── ...      # Other UI components
├── utils/       # Utility classes and functions
└── ...          # Other project files
```

## Acknowledgments
- Special thanks to the Bangkit program for providing resources and guidance throughout the project.
- The Android and AI/ML communities for their valuable open-source contributions.
