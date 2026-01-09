# 📖 Library Management System

A **full‑stack Library Management System** built with **Java, Spring Boot, and MySQL**.  
The project simulates a real‑world library platform with book inventory, subscriptions, payments, security, and user management.

It is designed as a **learning + production‑style project**, demonstrating clean architecture, REST APIs, JWT authentication, and role‑based access control.

---

## ✨ What You Will Build

- 📚 Complete **Book Inventory Management**
- 🗂️ **Hierarchical Genres** (parent → child → unlimited depth)
- 🔄 Book **Borrowing & Returns** with due dates
- ⏳ **Reservation / Hold Queue** for unavailable books
- 💰 Automatic **Overdue Fines** (partial & full payments)
- 💳 **Subscription Plans & Membership Control**
- ⭐ Verified **Book Reviews & Ratings**
- ❤️ **Wishlist** feature
- 📊 **Admin Dashboard & Analytics**
- 🔐 **JWT Authentication** + Role‑based Authorization
- 🌐 Google Login (OAuth)
- 💸 Payment Gateway Integration (Razorpay)

---

## 🔐 Core Features

### 👤 User & Security
- JWT‑based authentication
- Role‑based access (USER / ADMIN)
- Secure signup & login
- Protected API endpoints

### 📚 Library Management
- Add / update / delete books
- Genre management (tree structure)
- Book availability tracking
- Borrow, return, and renew books

### 💳 Subscriptions & Payments
- Multiple subscription plans
- Auto‑renewal support
- Payment tracking
- Active / expired subscriptions

### 🧾 Engagement
- Book reviews & ratings
- Wishlist management
- Reservation system

---

## 🛠️ Tech Stack

| Layer | Technology |
|-----|-----------|
| Frontend | HTML, CSS |
| Backend | Java, Spring Boot |
| Security | Spring Security, JWT |
| Database | MySQL |
| Server | Tomcat |
| Tools | Git, GitHub, Postman |

---

## 🔌 API Usage (Postman Examples)

### 📂 Genres

**GET**
```
GET /api/genres
GET /api/genres/top-level
GET /api/genres/{id}
```

**POST – Create Genre**
```json
{
  "code": "Fiction",
  "name": "Fiction",
  "description": "Suspense & investigation stories",
  "displayOrder": 1,
  "active": true,
  "parentGenreId": null
}
```

**PUT – Update Genre**
```json
{
  "id": 1,
  "code": "Non-Fiction",
  "name": "Non-Fiction",
  "description": "Educational & real stories",
  "displayOrder": 1,
  "active": true
}
```

**DELETE**
```
DELETE /api/genres/{id}        (soft delete)
DELETE /api/genres/{id}/hard   (hard delete)
```

---

### 📘 Books

**POST – Create Book**
```json
{
  "isbn": "978-3-16-148410-0",
  "title": "The Art of Java Development",
  "author": "John Doe",
  "genreId": 1,
  "publisher": "Mir",
  "publicationDate": "2023-05-15",
  "language": "English",
  "pages": 420,
  "description": "A comprehensive guide to mastering Java",
  "totalCopies": 1,
  "availableCopies": 1,
  "price": 499.99,
  "active": true
}
```

**GET**
```
GET /api/books
GET /api/books/{id}
```

---

### 🔑 Authentication

```
POST /auth/signup
POST /auth/login
```

```json
{
  "email": "testtest@gmail.com",
  "password": "testtest"
}
```

---

### 💳 Subscription Plans

```
GET  /api/subscription-plans
POST /api/subscription-plans/admin/create
PUT  /api/subscription-plans/admin/{id}
```

**Example Plan**
```json
{
  "planCode": "PREMIUM_90",
  "name": "Premium 90 Days",
  "description": "Full access with higher limits",
  "durationDays": 90,
  "price": 1299.00,
  "currency": "USD",
  "maxBooksAllowed": 20,
  "maxDaysPerBook": 15,
  "isActive": true
}
```

---

### 🔁 Subscriptions

**Subscribe (JWT required)**
```
POST /api/subscriptions/subscribe
```

```json
{
  "planId": 2,
  "startDate": "2026-01-01",
  "autoRenew": true,
  "notes": "First-time subscription"
}
```

```
GET /api/subscriptions/user/active
GET /api/subscriptions/admin
```

---

### 💰 Payments, Loans & More

```
GET  /api/payments
POST /api/book-loans/checkout
GET  /api/fines
GET  /api/fines/my
POST /api/reservations
POST /api/wishlist
POST /api/reviews
```

---

## 📸 Screenshots

> Sample UI views of the system

![Dashboard](screenshots/dashboard.png)

---

## 🧪 How to Run Locally

1. Install **Java 17+** and **MySQL** (XAMPP recommended)
2. Clone the repository:
   ```bash
   git clone https://github.com/your-username/library.git
   ```
3. Import database schema from `sql/database.sql`
4. Update `application.properties`
5. Run the Spring Boot application
6. Open browser:
   ```
   http://localhost:5000
   ```

---

## 🛡️ Default Admin Credentials

```
Username: admin
Password: admin123
```

---

## 📌 Notes

- All IDs use `Long`
- JWT is required for protected APIs
- Prices are stored as numeric values

---

## ⭐ Contribution

Feel free to fork this repository, submit issues, or open pull requests.

Happy coding 🚀

