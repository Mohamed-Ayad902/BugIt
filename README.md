# 🐞 Bug It: Android Client

A robust, **Offline First** bug tracking client built with **Jetpack Compose**, **MVI (Model-View-Intent)**, and **WorkManager**. This application serves as the presentation layer, consuming the core framework to deliver a seamless bug reporting experience.

## 🏗️ Architecture & Flow
- **Pattern**: MVI + Clean Architecture.
- **Reliability**: Implements an **Offline Queue** pattern. The UI saves bug data to Room instantly, and WorkManager orchestrates background synchronization even after process death.
- **Resilience**: Features **Checkpoint Retries**. The system tracks the sync lifecycle; if a Google Sheet sync fails after an image upload, the worker resumes exactly where it left off, preventing redundant data usage.

## 📱 Responsive Design System
The UI is built entirely in **Jetpack Compose** using a custom **Adaptive Design System**:
- **Material 3**: Full implementation of M3 color theming, shapes, and motion.
- **Adaptive Layouts**: Seamless transition between bottom navigation for mobile and navigation rails for wider screens, ensuring the bug reporting form remains ergonomic on all devices.

## 🛠️ Key Functionality
- **Multi Source Input**: Capture via in app gallery or receive images from other apps via **External Intents** (e.g., Sharing from Gallery), or capturing a screenshot within.
- **Background Sync**: Automated 12 hour sweep worker to identify and retry failed reports.
- **Reactive UX**: The UI observes the database state in real time, displaying live status updates (Pending → Uploading → Success).

## 🔐 Security Standards
- **Encrypted Credentials**: API keys and Google Service JSONs are managed by the Core module via **NDK/JNI**.
- **Signature Masking**: The native layer enforces signature checks to prevent unauthorized binary tampering.

## 🚀 Setup & Installation
1. Ensure the [BugIt Core Project](https://github.com/Mohamed-Ayad902/BugItCore) is published to `mavenLocal` with their 2 modules.
2. Clone this repository: `https://github.com/Mohamed-Ayad902/BugIt`
3. Sync the project and run on an Android device.

## 📸 Media & Assets

<p align="center">
  <img src="https://github.com/user-attachments/assets/5fdfc9f8-033e-4917-8d91-728c0d6e7eb0" width="30%" />
  <img src="https://github.com/user-attachments/assets/8b8ed764-bf38-4509-b7b1-b168d9b831d7" width="30%" />
  <img src="https://github.com/user-attachments/assets/b35b5c2f-082a-422f-876d-7096cb242502" width="30%" />
</p>

<p align="center">
  <img src="https://github.com/user-attachments/assets/1744da64-2446-4857-ac94-d5fd57107049" width="30%" />
  <img src="https://github.com/user-attachments/assets/d9781c78-11cb-4688-a110-a733ecea9565" width="30%" />
  <img src="https://github.com/user-attachments/assets/a4847d7b-4ab3-4565-9fef-9942cc79c82b" width="30%" />
</p>

<p align="center">
  <img src="https://github.com/user-attachments/assets/10eca129-4a4f-421f-afc6-d9a1373adb9c" width="30%" />
  <img src="https://github.com/user-attachments/assets/29b4cf38-61c4-409a-967d-bf6fb96b5a7a" width="30%" />
  <img src="https://github.com/user-attachments/assets/45ddf01d-cc9a-4577-9136-bd9e73bc6028" width="30%" />
</p>

<p align="center">
  <img src="https://github.com/user-attachments/assets/e7fe4fb5-af36-43c4-b645-13c35b6523e0" width="30%" />
  <img src="https://github.com/user-attachments/assets/e0c8e2ae-b1ef-45a7-93c9-0180c023dd22" width="30%" />
</p>

---

### 🔗 Project Links
- **📊 [Live Google Sheet Tracker](https://docs.google.com/spreadsheets/d/1NYmmRxmqRlP-sFXZ71t_jJlaH-bArnxmVWwTxMLIUsY/edit?gid=1519273619#gid=1519273619)**
- **🎥 [Watch the Demo Video](https://drive.google.com/file/d/1lRKFnXJNsLe0D3eRmjAbP63yCfnSR3TN/view?usp=sharing)**
- **📦 [Download the APK](https://drive.google.com/file/d/1NFXSX15sfCQnO5lefwfylgaowEawW83j/view?usp=sharing)**
---
