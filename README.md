# 📖 Library Management System

A full-stack **Java + MySQL** web application for managing a modern library.
The system covers book inventory, borrowing workflows, subscriptions,
payments, reviews, and role-based access control using JWT.

This project demonstrates **real-world backend architecture**, secure
authentication, and scalable CRUD design suitable for production use.

---

## 🎯 What This Project Covers

✔ Complete **Book Inventory Management**  
✔ **Hierarchical Genres** (parent → child, unlimited depth)  
✔ **Borrow / Return Workflow** with due dates & renewals  
✔ **Reservation & Hold Queue** for unavailable books  
✔ **Automatic Overdue Fines** (partial & full payments)  
✔ **Subscription Plans & Membership Control**  
✔ **Verified Book Reviews & Ratings**  
✔ **Wishlist Management**  
✔ **Admin Dashboard & Analytics**  
✔ **JWT Authentication + Google Login**  
✔ **Payment Gateway Integration (Razorpay ready)**  

---

## ✨ Key Features

### 🔐 Authentication & Security
- JWT-based authentication
- Role-based access (USER / ADMIN)
- Secure endpoints with Spring Security

### 📚 Library Operations
- Add, update, delete books
- Track available & borrowed copies
- Genre hierarchy with parent/child relations

### 🔄 Borrowing & Reservations
- Checkout & return books
- Reservation queue system
- Auto status updates

### 💳 Subscriptions & Payments
- Subscription plans (Basic, Premium, etc.)
- Active / expired subscription tracking
- Payment & activation flow

### ⭐ User Engagement
- Wishlist
- Reviews & ratings (verified readers only)
- User profile management

---

## 🧰 Tech Stack

| Layer        | Technology |
|--------------|------------|
| Frontend     | HTML, CSS (Vanilla) |
| Backend      | Java, Spring Boot |
| Security     | Spring Security, JWT |
| Database     | MySQL |
| Server       | Tomcat |
| Tools        | Git, GitHub, Postman |

---

## 🔌 API Examples (Postman)

### 📂 Genres

**Get**
```http
GET /api/genres
GET /api/genres/top-level
GET /api/genres/{id}
```

**Create**
```http
POST /api/genres/create
```

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

---

### 📘 Books

**Create Book**
```http
POST /api/books
```

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
  "coverImageUrl": "https://images.pexels.com",
  "active": true
}
```

---

## 🧪 Run Locally

1. Clone the repository
2. Import database schema from `sql/database.sql`
3. Configure DB credentials in `application.properties`
4. Run the application
5. Open http://localhost:5000

---

## 🔑 Default Admin Credentials

Username: admin  
Password: testtest

---

## 📜 License

MIT License
