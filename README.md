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

