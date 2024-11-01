# Movie-App-springboot
Complete Movie App in spring boot using Authentication, Authorization Using Spring Security and JWT, Different roles, Custom Exception handling, Lombok, JPA, Hibernate, Logging and connected to PSQL.

# Roles
## Admin => can do everything but an admin only can create movie, create actor, create genre.
## User => can do everything except those which is specific to admin only.

# EndPoints
## User

1- Register => localhost:8080/auth/register **POST**
{
    "userName": "ahmed",
    "password": "m@123",
    "email": "Mohamed@example.com",
    "role": ["user"]
}

2- Login => localhost:8080/auth/login **POST**
{
    "userName": "ahmed",
    "password": "m@123"
}
then user gets a jwt token and use it in each request. 

## Movie 
1- Create Movie => localhost:8080/movies/admin **POST** **ADMIN ONLY**
{
    "title" : "romantic film",
    "description" : "similar movies functions test",
    "duration" : 100,
    "director": "Christofar Nolan",
    "releaseDate": "2024-08-07T00:00:00",
    "posterUrl": "https://example.com/inception.jpg",
    "trailerUrl": "https://example.com/inception-trailer.mp4",
    "imdbRating": 7.8,
    "imdbRatingCount": "3m",
    "actors_id": [
        1,
        2
    ],
    "genres": [
        "Romance"
    ]
}

2- Get Movie Details by id => localhost:8080/movies/18 **GET**
### Reposonse Sample: 
{
    "message": "Movie details retrieved successfully",
    "code": 200,
    "data": {
        "movieId": 18,
        "title": "romantic film",
        "description": "similar movies functions test",
        "duration": 100,
        "director": "Christofar Nolan",
        "releaseDate": "2024-08-06T00:00:00",
        "posterUrl": "https://example.com/inception.jpg",
        "trailerUrl": "https://example.com/inception-trailer.mp4",
        "genreSet": [
            {
                "id": 1,
                "name": "Romance"
            }
        ],
        "actorSet": [
            {
                "id": 1,
                "name": "leonardo",
                "image": "url"
            },
            {
                "id": 2,
                "name": "Mohamed Hamdy",
                "image": "url"
            }
        ],
        "imdbRating": 7.8,
        "imdbRatingCount": "3m"
    }
}

3- Get all Movies => localhost:8080/movies/getAll **GET**  

4- Search for a movie By GENRE, ACTORS, DIRECTOR, TITLE => localhost:8080/movies/search/keyWord **GET**

5- Get Similar Movies by id => localhost:8080/movies/similarMovies/16 **GET**

6- Get top rated movies => localhost:8080/movies/topRated **GET**

7- Filter by genre => localhost:8080/movies/filterByGenre **GET**

8- Filter by actor => localhost:8080/movies/filterByActor **GET**

9- Filter by year => localhost:8080/movies/filterByYear **GET**

10- Filter by rate => localhost:8080/movies/filterByRate/5 **GET**

11- Get new moview => localhost:8080/movies/newMovies **GET**

## Genre

1- Create Genre => localhost:8080/genre/admin **POST** **ADMIN ONLY**
{
    "name" : "TEST"
}

2- Get all genres => localhost:8080/genre/getAll **GET**

## Actor

1- Create Actor => localhost:8080/actor/admin **POST** **ADMIN ONLY**
{
    "name": "mhmd",
    "image": "url" 
}





