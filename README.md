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
![UML](docs/escape_room_mermaid.mmd)

---
### 💾 UML

classDiagram
%% Auto-generated from your project structure
class AdminMenu {
}
class CertificateMenu {
}
class EscapeRoomMenu {
}
class InventoryMenu {
}
class SalesMenu {
}
class Main {
+ main(String[]) : void
}
class DatabaseConfig {
+ getConnection() : Connection
}
class DecorationDaoImpl {
- tableName : String
+ save(Decoration) : boolean
+ findAll() : List
+ findById(int) : Optional
+ remove(Decoration) : boolean
}
class EscapeRoomDaoImpl {
- tableName : String
+ save(EscapeRoom) : boolean
+ findAll() : List
+ findById(int) : Optional
+ remove(EscapeRoom) : boolean
}
class GenericDao {
<<interface>>
+ save(T) : boolean
+ findAll() : List
+ findById(int) : Optional
+ remove(T) : boolean
}
class HintDaoImpl {
- tableName : String
+ save(Hint) : boolean
+ findAll() : List
+ findById(int) : Optional
+ remove(Hint) : boolean
}
class PlayerDaoImpl {
- tableName : String
+ save(Player) : boolean
+ findAll() : List
+ findById(int) : Optional
+ remove(Player) : boolean
+ findByEmail(String) : Optional
+ updateSubscriptionStatus(String, boolean) : boolean
+ isEmailRegistered(String) : boolean
}
class RewardDaoImpl {
- tableName : String
+ save(Reward) : boolean
+ findAll() : List
+ findById(int) : Optional
+ remove(Reward) : boolean
}
class RoomDaoImpl {
- tableName : String
+ save(Room) : boolean
+ findAll() : List
+ findById(int) : Optional
+ remove(Room) : boolean
+ findByEscapeRoomId(int) : List
}
class TicketDaoImpl {
- tableName : String
+ save(Ticket) : boolean
+ findAll() : List
+ findById(int) : Optional
+ remove(Ticket) : boolean
+ findByPlayerId(int) : List
}
class DataNotFoundException {
}
class DatabaseOperationException {
}
class DecorationNotFoundException {
}
class EscapeRoomNotFoundException {
}
class HintNotFoundException {
}
class InvalidDataException {
}
class PlayerAlreadySubscribedException {
}
class PlayerNotFoundException {
}
class PlayerNotSubscribedException {
}
class RewardNotFoundException {
}
class RoomNotFoundException {
}
class TicketNotFoundException {
}
class AdminMenu {
}
class CertificateMenu {
}
class EscapeRoomMenu {
}
class InventoryMenu {
}
class SalesMenu {
}
class Decoration {
- id : Integer
- name : String
- material : String
- price : Double
- roomId : Integer
}
class Difficulty {
<<enumeration>>
EASY
MEDIUM
HARD
}
class EscapeRoom {
- id : Integer
- name : String
}
class Hint {
- id : Integer
- description : String
- theme : String
- price : Double
- roomId : Integer
}
class Player {
- id : Integer
- name : String
- email : String
- subscribed : boolean
+ update(String) : void
}
class Reward {
- id : Integer
- playerId : Integer
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
class Room {
- id : Integer
- name : String
- difficulty : Difficulty
- price : Double
- escapeRoomId : Integer
}
class Ticket {
- id : Integer
- playerId : Integer
- roomId : Integer
- price : Double
- purchaseDate : LocalDateTime
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
class CertificateService {
- playerDao : PlayerDaoImpl
- roomDao : RoomDaoImpl
+ generateCertificateForPlayer(int, int) : void
+ listPlayers() : List
}
class EscapeRoomService {
- escapeRoomDao : EscapeRoomDaoImpl
+ createEscapeRoom(String) : EscapeRoom
+ listEscapeRooms() : List
+ findAllEscapeRooms() : List
}
class InventoryService {
- roomDao : RoomDaoImpl
- hintDao : HintDaoImpl
- decorationDao : DecorationDaoImpl
- observers : List
+ addRoom(Room) : void
+ addHint(Hint) : void
+ addDecoration(Decoration) : void
+ subscribe(Observer) : void
+ unsubscribe(Observer) : void
+ notifyObservers(String) : void
+ getObservers() : List
}
class SalesService {
- playerDao : PlayerDaoImpl
- ticketDao : TicketDaoImpl
+ registerPlayer(Player) : Player
+ subscribeExistingPlayer(String) : void
+ unsubscribePlayer(String) : void
+ purchaseTicket(Ticket) : Ticket
+ findTicketById(int) : Optional
+ deleteTicketById(int) : boolean
+ listPlayers() : List
+ listTickets() : List
}
class ConnectionTestTest {
}
class InputUtils {
+ readInt(Scanner) : int
+ readDouble(Scanner) : double
+ readNonEmptyString(Scanner, String) : String
+ pause(Scanner) : void
}
Main --> AdminMenu
GenericDao <|.. EscapeRoomDaoImpl
GenericDao <|.. RoomDaoImpl
GenericDao <|.. HintDaoImpl
GenericDao <|.. DecorationDaoImpl
GenericDao <|.. PlayerDaoImpl
GenericDao <|.. TicketDaoImpl
GenericDao <|.. RewardDaoImpl
Observer <|.. Player
Subject <|.. InventoryService
CertificateService --> PlayerDaoImpl
CertificateService --> RoomDaoImpl
EscapeRoomService --> EscapeRoomDaoImpl
InventoryService --> RoomDaoImpl
InventoryService --> HintDaoImpl
InventoryService --> DecorationDaoImpl
SalesService --> PlayerDaoImpl
SalesService --> TicketDaoImpl
Player --> Ticket
Player --> Reward
Room --> Hint
Room --> Decoration
Ticket --> Room
Reward --> RewardType
Room --> Difficulty

%% Styling
classDef app fill:#F0FFF4,stroke:#2F855A;
class Main app
classDef config fill:#F3E8FF,stroke:#805AD5;
class DatabaseConfig config
classDef dao fill:#E7F7ED,stroke:#38A169;
class DecorationDaoImpl EscapeRoomDaoImpl GenericDao HintDaoImpl PlayerDaoImpl RewardDaoImpl RoomDaoImpl TicketDaoImpl dao
classDef exception fill:#FFF5F5,stroke:#C53030;
class DataNotFoundException DatabaseOperationException DecorationNotFoundException EscapeRoomNotFoundException HintNotFoundException InvalidDataException PlayerAlreadySubscribedException PlayerNotFoundException PlayerNotSubscribedException RewardNotFoundException RoomNotFoundException TicketNotFoundException exception
classDef menu fill:#FFF7D6,stroke:#C9A227;
class AdminMenu CertificateMenu EscapeRoomMenu InventoryMenu SalesMenu menu
classDef model fill:#E6F0FF,stroke:#5B8DEF;
class Decoration Difficulty EscapeRoom Hint Player Reward RewardType Room Ticket model
classDef model_observer fill:#E6F0FF,stroke:#5B8DEF,stroke-dasharray: 3 3;
class Observer Subject model_observer
classDef service fill:#FFEDEB,stroke:#E53E3E;
class CertificateService EscapeRoomService InventoryService SalesService service
classDef tests fill:#EDF2F7,stroke:#718096;
class ConnectionTestTest tests
classDef utils fill:#E8F6F8,stroke:#2B6CB0;
class InputUtils utils

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