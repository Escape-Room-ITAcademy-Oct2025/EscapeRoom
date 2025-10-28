# 🧩 Escape Room Management System

---

## 📄 Description – Exercise Statement

This project consists of building a **console-based management system** for an Escape Room business.  
It allows administrators to manage escape rooms, rooms, hints, and decorations, while players receive automatic notifications when new events are created.

The goal of the exercise is to apply **Object-Oriented Programming (OOP)** principles, database integration through **JDBC**, and implement the **Observer design pattern** to simulate event-driven updates.

Developed as part of the **Java Back-End Development Bootcamp** at *IT Academy Barcelona*.

---

## 💻 Technologies Used

| Category | Tools / Technologies |
|-----------|----------------------|
| **Language** | Java 21 |
| **Database** | MySQL 8 |
| **Persistence** | JDBC |
| **Build Tool** | Maven |
| **Containerization** | Docker & Docker Compose |
| **Testing** | JUnit 5, AssertJ |
| **IDE** | IntelliJ IDEA |
| **Version Control** | Git & GitHub |

---

## 📋 Requirements

Before running this project, ensure you have installed:

- ☕ **Java JDK 21**
- 🧱 **Apache Maven 3.9+**
- 🐳 **Docker Desktop** (or Docker Engine + Docker Compose)
- 🗄️ **MySQL 8.0+**
- 🧰 IDE such as IntelliJ IDEA or VS Code (optional but recommended)

---

## 🛠️ Installation

### 1️⃣ Clone the repository
```bash
git clone https://github.com/Escape-Room-ITAcademy-Oct2025/EscapeRoom.git
cd EscapeRoom
```

### 2️⃣ Start the MySQL container using Docker
```bash
cd docker
docker compose up -d
```

This will start:
- **MySQL** on port `3307`

### 3️⃣ Verify that the database is loaded

The script [`docker/init/init.sql`](docker/init/init.sql) automatically creates:
- Tables (`escape_room`, `room`, `hint`, `decoration`, `player`, `ticket`, `reward`)
- Sample records for initial testing

---

## ▶️ Execution

### Option 1 – Run via IntelliJ IDEA
1. Open the project as a Maven project.
2. Ensure the database container is running.
3. Run the `Main` class.
4. Follow the on-screen menu to manage Escape Rooms, Rooms, and other entities.

### Option 2 – Run from the terminal
```bash
mvn clean compile exec:java
```

### Option 3 – Execute tests
```bash
mvn test
```

---

## 🌐 Deployment

The project is designed for **local or educational environments**, but can easily be deployed to production using Docker.

### 1️⃣ Build the Docker image
```bash
docker build -t escaperoom-app .
```

### 2️⃣ Run together with MySQL
```bash
docker compose up -d
```

### 3️⃣ Environment variables (optional)
You can customize your database connection in `src/main/resources/db.properties`:

```properties
db.url=jdbc:mysql://localhost:3307/escaperoom_db
db.user=escaperoom
db.password=escaperoom123
```

---

### 🧑‍💻 Authors

- **Adrià Lorente** – *Service & Integration Lead*
- **Andrés [Last Name]** – *Data Layer Lead*

Developed at [IT Academy Barcelona](https://www.barcelonactiva.cat/itacademy)  
as part of the **Java Back-End Development Bootcamp** (2025).