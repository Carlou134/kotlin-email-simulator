# Kotlin Email Simulator

A console-based email inbox simulator written in Kotlin. It lets a user register, log in, and manage emails across folders — all in memory, no database required.

## Features

- User registration and login with credential validation
- Send emails to other registered users (copy saved to `SENT`, delivered to recipient's `INBOX`)
- Receive simulated incoming emails
- List emails by folder: `INBOX`, `SENT`, `ARCHIVE`, `SPAM`
- Mark emails as read
- Move emails between folders
- Delete emails
- Ownership checks: users can only operate on their own emails

## Tech Stack

- Kotlin 2.0.20 (JVM)
- Gradle (Kotlin DSL) with wrapper
- `kotlin-test` + JUnit Platform for testing

## Project Structure

The code is organized by feature (screaming architecture), each split into layers:

```
src/main/kotlin
├── Main.kt                         # Console entry point and interactive menu
├── email
│   ├── domain                      # Email, Folder, EmailRepository
│   ├── infrastructure              # InMemoryEmailRepository
│   └── service                     # InboxService, EmailNotFoundException
└── user
    ├── domain                      # User, UserRepository
    ├── infrastructure              # InMemoryUserRepository
    └── service                     # Authentication / user exceptions
```

- **domain**: entities and repository contracts
- **infrastructure**: in-memory repository implementations
- **service**: application logic and domain exceptions

## Getting Started

### Prerequisites

- A JDK (17+ recommended)
- IntelliJ IDEA (recommended)

### Run

Open the project in IntelliJ IDEA and run the `main` function in `src/main/kotlin/Main.kt`.

> The Gradle `application` plugin is not configured yet, so `./gradlew run` is not available.

### Test

```bash
./gradlew test
```

## Usage

On startup you will be asked to register and then log in. After that, the interactive menu appears:

```
---Menú-------
 1) Enviar correo              (Send email)
 2) Listar carpetas            (List folder)
 3) Marcar leído               (Mark as read)
 4) Mover correo               (Move email)
 5) Eliminar correo            (Delete email)
 6) Recibir un correo simulado (Receive simulated email)
 7) Salir                      (Exit)
```

Operations on a specific email (mark as read, move, delete) require the email's UUID, which is shown when listing a folder.

> Note: the console UI is in Spanish.

## Limitations

- Data is stored in memory only and is lost when the app exits
- Passwords are stored in plain text (educational project)

## Author

Carlos Vásquez

## License

This project is licensed under the MIT License — see the [LICENSE](LICENSE) file for details.
