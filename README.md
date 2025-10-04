# ☕ Coffee Shop Management System

A robust **Spring Boot** application for managing coffee shop operations — including users, menus, orders, tables, reservations, and real-time event streaming using **Apache Kafka**.  
Built with **PostgreSQL/MySQL**, **Spring Boot 3**, and **Spring Kafka**.

---

## 🚀 Features

### 🧑‍💼 User Management
- Role-based access control (`admin`, `guest`)
- Secure authentication with encrypted passwords
- Automatic timestamps (`created_at`, `updated_at`)

### 🍽 Menu Management
- CRUD operations for menu items
- Menu size options (`S`, `M`, `L`)
- Active/inactive status handling

### 🧾 Order Management
- Create and update orders
- Status tracking: `open`, `closed`, `paid`, `cancelled`
- Auto-calculation of total amount and item subtotals

### 🪑 Table & Reservation Management
- Manage coffee tables and availability
- Handle reservations with statuses:
  - `pending`, `confirmed`, `cancelled`, `completed`

### 📊 Reports
- Daily reports with:
  - Total customers
  - Total orders
  - Total revenue

### ⚡ Real-time Event Streaming (Kafka)
- **Kafka Producers** send messages when new orders or reservations are created
- **Kafka Consumers** listen and process updates for analytics, dashboards, or notifications
- Enables **scalable event-driven architecture**

### 🧠 API Documentation
- Interactive REST API documentation via **Swagger UI**

---

## 🛠️ Tech Stack

| Category | Technology |
|-----------|-------------|
| Backend | Spring Boot (Java 17+) |
| Database | PostgreSQL / MySQL |
| Messaging | Apache Kafka |
| ORM | Spring Data JPA |
| API Docs | Springdoc OpenAPI (Swagger UI) |
| Build Tool | Maven |
| Logging | SLF4J + Logback |

---
## ⚙️ Installation & Setup

### 1️⃣ Clone the repository
```bash
git https://github.com/phamdongchinh683/backend-coffee-shop-manager
cd coffee-shop-system
