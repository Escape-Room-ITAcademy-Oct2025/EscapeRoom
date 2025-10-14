# 🧩 Escape Room Project

Virtual Escape Room management system built in Java 21, using MySQL (via Docker) as database.

---

## 🚀 Getting Started

### 1. Start MySQL container
```bash
cd docker
docker compose up -d
```

### 2. Run unit tests to verify connection
```bash
mvn clean test
```

### 3. Run the main app
```bash
mvn exec:java -Dexec.mainClass="com.escaperoom.app.Main"
```

---

## 🧱 Tech Stack
- Java 21
- Maven
- MySQL 8.0 (Docker)
- JUnit 5 + AssertJ

---

## 🧩 Project Structure
```
src/
 ├── main/java/com/escaperoom/
 │    ├── app/ → main classes (e.g. Main.java)
 │    └── config/ → configuration classes (e.g. DatabaseConfig.java)
 ├── resources/ → configuration files (e.g. db.properties)
 └── test/ → unit tests (e.g. ConnectionTestTest.java)
```

---

## 🧑‍💻 Authors
- Andrés Calvo
- Adrià Lorente