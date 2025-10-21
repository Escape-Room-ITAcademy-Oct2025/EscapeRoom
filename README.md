# 🧩 Virtual Escape Room Management System

## 🎯 Overview

A Java console-based application to manage a **virtual Escape Room**, developed following a layered architecture (DAO → Service → Menu → Model).  
It connects to a **MySQL** database to manage rooms, hints, decorations, tickets, players, and rewards.

---

## 🧱 Architecture

### 🧩 Project structure

```
src/main/java/
├── app/
│   └── Main.java
├── config/
│   └── DatabaseConfig.java
├── dao/
│   ├── GenericDao.java
│   ├── EscapeRoomDaoImpl.java
│   ├── RoomDaoImpl.java
│   ├── HintDaoImpl.java
│   ├── DecorationDaoImpl.java
│   ├── PlayerDaoImpl.java
│   ├── TicketDaoImpl.java
│   └── RewardDaoImpl.java
├── service/
│   ├── EscapeRoomService.java
│   ├── InventoryService.java
│   ├── SalesService.java
│   └── CertificateService.java
├── menu/
│   ├── AdminMenu.java
│   ├── EscapeRoomMenu.java
│   ├── InventoryMenu.java
│   ├── SalesMenu.java
│   └── CertificateMenu.java
├── model/
│   ├── EscapeRoom.java
│   ├── Room.java
│   ├── Hint.java
│   ├── Decoration.java
│   ├── Player.java
│   ├── Ticket.java
│   ├── Reward.java
│   └── Difficulty.java
└── utils/
    └── InputUtils.java
```

---

## ⚙️ Database

**Database name:** `escape_room_db`  
**Engine:** MySQL  
**Encoding:** `utf8mb4_unicode_ci`

### 🗄️ Tables

- `escape_room` → Escape room metadata
- `room` → Rooms linked to escape rooms (difficulty, price)
- `hint` → Thematic hints linked to rooms
- `decoration` → Decorations for each room
- `player` → Registered players (name + email)
- `ticket` → Tickets sold to players (linked to room and player)
- `reward` → Certificates / achievements linked to players

### ✅ Relationships

| Table | Relationship | On Delete |
|--------|---------------|------------|
| `room.escape_room_id` | → `escape_room.id` | CASCADE |
| `hint.room_id` | → `room.id` | CASCADE |
| `decoration.room_id` | → `room.id` | CASCADE |
| `ticket.player_id` | → `player.id` | CASCADE |
| `ticket.room_id` | → `room.id` | CASCADE |
| `reward.player_id` | → `player.id` | SET NULL |

---

## 🧮 Features

| Category | Feature |
|-----------|----------|
| Escape Rooms | Create, list, and delete Escape Rooms |
| Inventory | Add / list / delete rooms, hints, decorations |
| Sales | Sell tickets, list sales, calculate total revenue |
| Certificates | Generate certificates for completed rooms |
| Validation | Input checked via `InputUtils` (int, double, strings) |

---

## 💻 How to Run

### Prerequisites

- Java 21+
- MySQL running locally (Docker or Workbench)
- JDBC driver configured

### Steps

1. Clone the repository and open it in IntelliJ IDEA.
2. Import it as a **Maven project** (if applicable).
3. Configure database access in `DatabaseConfig.java`.
4. Run the SQL script (`schema.sql`) to create the database and sample data.
5. Run `Main.java`.

```bash
javac -d out src/main/java/**/*.java
java -cp out app.Main
```

---


## 🏁 Summary

A full-stack backend console project with:
- Multi-layer architecture (DAO–Service–Menu)
- MySQL persistence
- Clean code & modular design
- Exception handling & input validation
- Enum + Optional usage across DAOs

---

## 🧑‍💻 Authors
- Andrés Calvo
- Adrià Lorente
