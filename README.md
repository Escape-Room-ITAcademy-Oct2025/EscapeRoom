# 🧩 Escape Room Management System

---

## 📄 Description – Exercise Statement

This project consists of building a **console-based management system** for an Escape Room business.  
It allows administrators to manage escape rooms, rooms, hints, and decorations, while players receive automatic notifications when new events are created.

The goal of the exercise is to apply **Object-Oriented Programming (OOP)** principles, database integration through **JDBC**, and implement the **Observer design pattern** to simulate event-driven updates.

Developed as part of the **Java Back-End Development Bootcamp** at *IT Academy Barcelona*.

---
### 🎯 Key Features

- [X] Create a new Escape Room with a unique name
- [X] Add a new room with its respective difficulty level
- [X] Incorporate thematic clues to enrich the gaming experience
- [X] Introduce decorative objects to create a unique atmosphere in the rooms
- [X] Show the updated inventory, displaying the available quantities of each element (rooms, hints, and decorative objects)
- [X] View the total value in euros of the virtual Escape Room inventory
- [X] Allow the removal of rooms, hints, or decorative objects from the inventory
- [X] Offer functionality to issue certificates for completing puzzles, recording player achievements during their Escape Room experience
- [X] Provide possible gifts or rewards in recognition of players' skills and problem-solving ability
- [X] Generate sales tickets for the different players
- [X] Calculate and display the total revenue generated from ticket sales for the virtual Escape Room
- [X] Notify users about important events in the Escape Room, such as the addition of new rooms or the creation of a new Escape Room
- [X] Allow users to register to receive notifications when relevant events occur

---
### 💾 Database Schema Overview

Below is the database structure for the Escape Room system:

![Database UML](docs/db-diagram.png)

---
## 🧩 UML Diagram

```mermaid
%%{init: {'theme': 'neutral', 'flowchart': {'defaultRenderer': 'elk'}} }%%
classDiagram
    direction TB

%% ===========================
%% MODEL
%% ===========================
    class EscapeRoom {
        - id : int
        - name : String
        + getId() : int
        + getName() : String
        + addRoom(Room)
    }

    class Room {
        - id : int
        - name : String
        - difficulty : Difficulty
        - price : double
        - escapeRoomId : int
    }

    class Difficulty {
        <<enumeration>>
        EASY
        MEDIUM
        HARD
    }

    class Hint {
        - id : int
        - description : String
        - theme : String
        - price : double
        - roomId : int
    }

    class Decoration {
        - id : int
        - name : String
        - material : String
        - price : double
        - roomId : int
    }

    class Player {
        - id : int
        - name : String
        - email : String
        - subscribed : boolean
        + update(String) : void
    }

    class Ticket {
        - id : int
        - playerId : int
        - roomId : int
        - price : double
        - purchaseDate : LocalDateTime
    }

    class Reward {
        - id : int
        - playerId : int
        - name : String
        - description : String
        - rewardType : RewardType
        - dateAwarded : LocalDateTime
    }

    class RewardType {
        <<enumeration>>
        HONOR
        UNITY
        PERSISTENCE
        CREATIVITY
        STONE
    }

    class Observer {
        <<interface>>
        + update(String) : void
    }

    class Subject {
        <<interface>>
        + subscribe(Observer)
        + unsubscribe(Observer)
        + notifyObservers(String)
    }

    Player ..|> Observer

%% ===========================
%% DAO
%% ===========================
    class GenericDao {
        <<interface>>
        + save(T) : boolean
        + findAll() : List<T>
        + findById(int) : Optional<T>
        + remove(T) : boolean
    }

    class EscapeRoomDaoImpl
    class RoomDaoImpl
    class HintDaoImpl
    class DecorationDaoImpl
    class PlayerDaoImpl
    class TicketDaoImpl
    class RewardDaoImpl

    GenericDao <|.. EscapeRoomDaoImpl
    GenericDao <|.. RoomDaoImpl
    GenericDao <|.. HintDaoImpl
    GenericDao <|.. DecorationDaoImpl
    GenericDao <|.. PlayerDaoImpl
    GenericDao <|.. TicketDaoImpl
    GenericDao <|.. RewardDaoImpl

%% ===========================
%% SERVICE
%% ===========================
    class EscapeRoomService
    class InventoryService
    class SalesService
    class CertificateService

    EscapeRoomService --> EscapeRoomDaoImpl
    InventoryService --> RoomDaoImpl
    InventoryService --> HintDaoImpl
    InventoryService --> DecorationDaoImpl
    InventoryService ..|> Subject
    SalesService --> PlayerDaoImpl
    SalesService --> TicketDaoImpl
    CertificateService --> PlayerDaoImpl
    CertificateService --> RoomDaoImpl

%% ===========================
%% MENU
%% ===========================
    class AdminMenu
    class EscapeRoomMenu
    class InventoryMenu
    class SalesMenu
    class CertificateMenu

    AdminMenu --> EscapeRoomMenu
    AdminMenu --> InventoryMenu
    AdminMenu --> SalesMenu
    AdminMenu --> CertificateMenu

    EscapeRoomMenu --> EscapeRoomService
    InventoryMenu --> InventoryService
    SalesMenu --> SalesService
    CertificateMenu --> CertificateService

%% ===========================
%% RELATIONSHIPS MODEL
%% ===========================
    EscapeRoom "1" --> "*" Room
    Room "1" --> "*" Hint
    Room "1" --> "*" Decoration
    Player "1" --> "*" Ticket
    Player "1" --> "*" Reward
    InventoryService ..> Observer : notifies
    InventoryService ..> Player : subscribed players

```

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

### 🧑‍💻 Authors

- [**Adrià Lorente**](https://github.com/alaw810)
- [**Andrés Calvo**](https://github.com/Andrescalvo22)

Developed at [IT Academy Barcelona](https://www.barcelonactiva.cat/itacademy)  
as part of the **Java Back-End Development Bootcamp** (2025).