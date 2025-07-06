# ✈️ Airlines Explorer App

An Android app to explore airline data using Jetpack Compose, Kotlin, MVVM, Hilt, and Moshi. Data is loaded from a local JSON file in the assets folder.

## 🚀 Features

- Displays airline data from a local JSON file located in the `assets/` directory.
- MVVM architecture for clean separation of concerns.
- Built using Jetpack Compose for modern UI.
- Navigation between screens using Jetpack Navigation Component.
- Data fetching with Coroutines and Flow.
- Dependency injection using Hilt.
- Simple and efficient offline data storage using a document-based approach.

---

## 🛠 Tech Stack

| Layer               | Technology                         |
|--------------------|-------------------------------------|
| **Language**        | Kotlin                              |
| **Architecture**    | MVVM                                |
| **UI Toolkit**      | Jetpack Compose (preferred)         |
| **Navigation**      | Jetpack Navigation Component        |
| **Networking**      | Retrofit                            |
| **Async Handling**  | Kotlin Coroutines + Flow            |
| **Dependency Injection** | Hilt                         |
| **Persistence**     | Document-based database (preferred) or Room |

---

## 📁 Project Structure

├── data/
│ ├── model/ # Airline data models
│ ├── repository/ # AirlineRepository interface and implementation
│ └── datasource/ # Asset or Room-based data source
├── di/ # Hilt modules
├── ui/
│ ├── screens/ # Composable screens (e.g., HomeScreen, DetailScreen)
│ └── components/ # Reusable composables
├── navigation/ # App navigation graph
├── utils/ # Helper functions or extensions
└── MainActivity.kt # Entry point of the application


---

## 📦 How to Run

1. **Clone the Repository**

   ```bash
   git clone https://github.com/thakurvibha/AirlineExplorerAssignment.git

