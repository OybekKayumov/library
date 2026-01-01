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