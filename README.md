Library Management System

java full-stack project

postman:
    get
        localhost:5000/api/genres
        localhost:5000/api/genres/top-level
        localhost:5000/api/genres/1

    post
        localhost:5000/api/genres/create
        {    
        "code": "Fiction",
        "name": "Fiction",
        "description": "Suspene & investigation stories",
        "displayOrder": 1,
        "active": true,
        "parentGenreId": null
        }

    post GenreDTO
        localhost:5000/api/genres/create
        {    
            "code": "Mystery",
            "name": "Mystery",
            "description": "Suspene & investigation stories",
            "displayOrder": 1,
            "active": true,
            "parentGenreId": 1
        }

    put - update
        localhost:5000/api/genres/1
        {
        "id": 1,
        "code": "Non-Fiction",
        "name": "Non-Fiction",
        "description": "Suspene & investigation stories",
        "displayOrder": 1,
        "active": true,
        "parentGenreId": null,
        "parentGenreName": null,
        "subGenre": null,
        "bookCount": null,
        "createdAt": "2025-12-28T11:10:48.529412",
        "updatedAt": "2025-12-28T11:10:48.529412"
        }

    delete 
    soft
        localhost:5000/api/genres/2
    hard
        localhost:5000/api/genres/2/hard

    create a Book
        localhost:5000/api/books
        {    
            "isbn": "978-3-16-148410-0",
            "title": "The Art of Java Development",
            "author": "John Doe",
            "genreId": 1,
            "publisher": "Mir",
            "publicationDate": "2023-05-15",
            "language": "English",
            "pages": 420,    
            "description": "A coomprehensive guide to mastering Java ...",
            "totalCopies": 1,
            "availableCopies": 1,
            "price": 499.99,
            "coverImageUrl": "https://images.pexels.com",
            "active": true    
        }

    get all Books
        localhost:5000/api/books

    get a Book by Id
        localhost:5000/api/books/1

    put - update the Book
        localhost:5000/api/books/1

add spring security

add User

add JWT token

add admin role

    localhost:5000/auth/signup
    localhost:5000/auth/login
        {       
        "email": "testtest@gmail.com",    
        "password": "testtest"    
        }

    localhost:5000/api/admin/books

add User Service

    localhost:5000/api/users/profile

add Subscription Plan
    
    localhost:5000/api/subscription-plans

    localhost:5000/api/subscription-plans/admin/create

        {
        "planCode": "BASIC_30",
        "name": "Basic 30 Days plan",
        "description": "Access to basic features ...",
        "durationDays": 30,
        "price": 499.00,    
        "maxBooksAllowed": 5,
        "maxDaysPerBook": 7,
        "displayOrder": 1,
        "isActive": true,
        "isFeatured": false,
        "badgeText": "Popular",
        "adminNotes": "Entry-level subscription plan"
        }

        {
        "planCode": "PREMIUM_90",
        "name": "Premium 90 Days plan",
        "description": "Full access with higher limits",
        "durationDays": 90,
        "price": 1299.00,
        "currency": "USD",    
        "maxBooksAllowed": 20,
        "maxDaysPerBook": 15,
        "displayOrder": 2,
        "isActive": true,
        "isFeatured": false,
        "badgeText": "Best Value",
        "adminNotes": "Recommended plan"
        }

    update
    localhost:5000/api/subscription-plans/admin/1

add Subscription

    post - Bearer Token - {{jwt}}
    localhost:5000/api/subscriptions/subscribe
        {
        "planId": 2,
        "startDate": "2026-01-01",
        "autorenew": true,
        "notes": "First-time subscription"
        }
    
    get all subscriptions
    localhost:5000/api/subscriptions/admin

    activate subscription
    localhost:5000/api/subscriptions/activate?subscriptionId=102&paymentId=1

    get
    localhost:5000/api/subscriptions/user/active

add Payments
    get all Payments
    localhost:5000/api/payments

add BookLoan
    post
    localhost:5000/api/book-loans/checkout

add Fine
    localhost:5000/api/fines
    localhost:5000/api/fines/my
    localhost:5000/api/fines/waive

add Reservation
    localhost:5000/api/reservations


# 📚 Library Management System

A full-stack Java + MySQL web application to manage book inventory, 
borrowing activity, returns, and admin access control. 
This project demonstrates real-world CRUD operations, secure login, and 
session-based workflows in a self-contained library system.

---

## 🔧 Features

- 🔐 Admin login/logout system (session-based)
- ➕ Add new books to the inventory
- 📖 View books with author, genre & date
- 📦 Issue books to students
- 📥 Return books and mark as available
- 📋 View issued logs with real-time status (Issued/Returned)
- 📊 Dashboard with total, issued & returned book stats
- 🌓 Dark mode toggle
- 📱 Mobile responsive layout
- 🚀 Optional deployment using 

---

## 🛠️ Tech Stack

- **Frontend:** HTML, CSS (vanilla)
- **Backend:** Java, Spring 4.x
- **Database:** MySQL (via MyAdmin)
- **Local Server:** Tomcat
- **Version Control:** Git + GitHub

---

## 📸 Screenshots

> 📌 _Here are a few screenshots to give you a glimpse of the application:_

### Dashboard
![Dashboard showing book stats](screenshots/dashboard.png)

---

## 🧪 How to Run Locally

1.  Install [Test](https://www.) or [XAMPP](https://www.)
2.  Clone the repository:
    ```bash
    git clone [https://github.com/*/library.git]
    (https://github.com/*/library.git)
    ```
3.  Move the project folder to your * `docs` or * `docs` directory.
4.  Start Java & MySQL from *.
5.  Import the SQL schema from `sql/database.sql` into MyAdmin.
6.  Visit: `http://localhost:5000`
    (Login with default credentials: `root` / `testtest` unless 
    changed.)

### 🛡️ Admin Credentials

**Default:**
```makefile
Username: root
Password: testtest