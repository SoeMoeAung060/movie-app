package com.soe.movieticketapp.domain.repository

import androidx.paging.PagingData
import com.soe.movieticketapp.data.remote.dto.CastResponseDTO
import com.soe.movieticketapp.data.remote.dto.DetailResponseDTO
import com.soe.movieticketapp.data.remote.dto.GenresResponseDTO
import com.soe.movieticketapp.data.remote.dto.TrailerResponseDTO
import com.soe.movieticketapp.data.remote.dto.WatchProviderResponse
import com.soe.movieticketapp.domain.model.Movie
import com.soe.movieticketapp.domain.model.Search
import com.soe.movieticketapp.util.MovieType
import com.soe.movieticketapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun getTrendingMovies(movieType: MovieType): Flow<PagingData<Movie>>
    suspend fun getSimilarMoviesAndTvSeries(movieType: MovieType, movieId: Int): Flow<PagingData<Movie>>
    suspend fun getSearchMovies(query: String): Flow<PagingData<Search>>
    suspend fun getNowPlayingMovies() : Flow<PagingData<Movie>>

    /** Non-paging data */
    suspend fun getCastAndCrewMovies(movieType: MovieType, movieId: Int): Resource<CastResponseDTO>
    suspend fun getGenreMovies(movieType: MovieType) : Resource<GenresResponseDTO>
    suspend fun getMovieProviders(movieType: MovieType, movieId: Int): Resource<WatchProviderResponse>
    suspend fun getTrailerMovies(movieType: MovieType, movieId: Int): Resource<TrailerResponseDTO>
    suspend fun getDetailMovies(movieType: MovieType, movieId: Int): Resource<DetailResponseDTO>


}