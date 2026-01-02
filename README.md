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