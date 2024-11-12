package com.soe.movieticketapp.util

import com.soe.movieticketapp.domain.model.Cast
import com.soe.movieticketapp.domain.model.Genre
import com.soe.movieticketapp.domain.model.Movie

const val BASE_URL = "https://api.themoviedb.org/3/"
const val API_KEY = "6bbb92d3287c6081aebb32fe6837e0c6"
const val BASE_BACKDROP_IMAGE_URL = "https://image.tmdb.org/t/p/w780/"
const val BASE_POSTER_IMAGE_URL = "https://image.tmdb.org/t/p/w500/"
const val LANGUAGE = "en-US"
const val DATABASE_NAME = "movie_database"

const val PAGE_SIZE = 10

val CATEGORY = listOf(
    "trending",
    "popular",
    "top_rated",
    "upcoming",
    "now_playing"
)
val GENRES = listOf(
    "Action",
    "Adventure",
    "Animation",
    "Comedy",
    "Crime",
    "Documentary",
    "Drama",
    "Family",
    "Fantasy",
    "History",
    "Horror",
    "Music",
    "Mystery",
    "Romance",
    "Science Fiction",
    "TV Movie",
    "Thriller",
    "War",
    "Western"
)


val Movie =  listOf(
    Movie(
        adult = false,
        backdropPath = "/path/to/backdrop2.jpg",
        posterPath = "/path/to/poster2.jpg",
        genreIds = listOf(16, 35),
        genres = listOf(Genre(3, "Animation"), Genre(4, "Comedy")),
        mediaType = "movie",
        firstAirDate = "2023-02-02",
        id = 2,
        imdbId = "tt7654321",
        originalLanguage = "en",
        originalName = "Original Name 2",
        overview = "This is the overview of the second movie.",
        popularity = 9.0,
        releaseDate = "2023-02-02",
        runtime = 90,
        title = "Movie Title 2",
        video = true,
        voteAverage = 8.0,
        voteCount = 200

    )
)


val Cast = listOf(
    Cast(
        adult = false,
        gender = 1,
        id = 2342,
        department = "Acting",
        name = "John Meillon",
        originalName = "John Meillon",
        popularity = 3.385,
        profilePath = "/k9x2VwKqiTxTKP1WIgOM1AtZf2G.jpg",
        castId = 3,
        character = "Walter Reilly",
        creditId = "52fe44f2c3a36847f80b3693",
        order = 2
    )
)