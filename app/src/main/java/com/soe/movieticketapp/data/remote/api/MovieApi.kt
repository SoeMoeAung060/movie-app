package com.soe.movieticketapp.data.remote.api

import com.soe.movieticketapp.data.remote.dto.CastResponseDTO
import com.soe.movieticketapp.data.remote.dto.DetailResponseDTO
import com.soe.movieticketapp.data.remote.dto.GenresResponseDTO
import com.soe.movieticketapp.data.remote.dto.MovieResponseDTO
import com.soe.movieticketapp.data.remote.dto.MultiSearchResponseDTO
import com.soe.movieticketapp.data.remote.dto.TrailerResponseDTO
import com.soe.movieticketapp.data.remote.dto.WatchProviderResponse
import com.soe.movieticketapp.util.API_KEY
import com.soe.movieticketapp.util.LANGUAGE
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET("trending/movie/day")
    suspend fun getTrendingMovies(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page: Int,
        @Query("language") language: String = LANGUAGE
    ): MovieResponseDTO


    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page: Int,
        @Query("language") language: String = LANGUAGE
    ): MovieResponseDTO


    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page: Int,
        @Query("language") language: String = LANGUAGE
    ): MovieResponseDTO

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page: Int,
        @Query("language") language: String = LANGUAGE
    ): MovieResponseDTO


    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page: Int,
        @Query("language") language: String = LANGUAGE
    ): MovieResponseDTO


    @GET("{movie_path}/{movie_id}")
    suspend fun getDetailMovies(
        @Path("movie_id") movieId : Int,
        @Path("movie_path") moviePath: String,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = LANGUAGE
    ): DetailResponseDTO


    @GET("trending/tv/day")
    suspend fun getTrendingTvSeries(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page: Int,
        @Query("language") language: String = LANGUAGE
    ): MovieResponseDTO

    @GET("tv/popular")
    suspend fun getPopularTvSeries(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page: Int,
        @Query("language") language: String = LANGUAGE
    ): MovieResponseDTO

    @GET("tv/top_rated")
    suspend fun getTopRateTvSeries(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page: Int,
        @Query("language") language: String = LANGUAGE
    ): MovieResponseDTO

    @GET("tv/on_the_air")
    suspend fun getOnTheAirTvSeries(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page: Int,
        @Query("language") language: String = LANGUAGE
    ): MovieResponseDTO


    @GET("movie/{movie_id}/similar")
    suspend fun getSimilarMovies(
        @Path("movie_id") movieId : Int,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page: Int,
        @Query("language") language: String = LANGUAGE
    ): MovieResponseDTO


    @GET("tv/{tv_id}/similar")
    suspend fun getSimilarTvShows(
        @Path("tv_id") tvId : Int,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page: Int,
        @Query("language") language: String = LANGUAGE
    ): MovieResponseDTO








    @GET("genre/movie/list")
    suspend fun getMovieGenres(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = LANGUAGE
    ): GenresResponseDTO

    @GET("genre/tv/list")
    suspend fun getTvShowGenres(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = LANGUAGE
    ): GenresResponseDTO


    @GET("movie/{movie_id}/credits")
    suspend fun getMoviesCastAndCrew(
        @Path("movie_id") movieId : Int,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = LANGUAGE,
    ): CastResponseDTO


    @GET("tv/{tv_id}/credits")
    suspend fun getTvShowCastAndCrew(
        @Path("tv_id") movieId : Int,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = LANGUAGE,
    ): CastResponseDTO





    @GET("search/multi")
    suspend fun getSearchMovies(
        @Query("query") query: String,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page: Int,
//        @Query("include_adult") includeAdult: Boolean = true,
        @Query("language") language: String = LANGUAGE
    ): MultiSearchResponseDTO


    //tv/{series_id}/watch/providers
    @GET("{movie_path}/{movie_id}/watch/providers?")
    suspend fun getWatchProviders(
        @Path("movie_path") moviePath: String,
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = API_KEY,
    ): WatchProviderResponse

    @GET("{movie_path}/{movie_id}/videos")
    suspend fun getWatchTrailersMovie(
        @Path("movie_path") moviePath: String,
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = API_KEY,
    ): TrailerResponseDTO


}