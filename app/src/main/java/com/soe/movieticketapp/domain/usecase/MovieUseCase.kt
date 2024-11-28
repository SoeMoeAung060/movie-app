package com.soe.movieticketapp.domain.usecase

import androidx.paging.PagingData
import com.soe.movieticketapp.data.remote.dto.CastResponseDTO
import com.soe.movieticketapp.data.remote.dto.DetailResponseDTO
import com.soe.movieticketapp.data.remote.dto.GenresResponseDTO
import com.soe.movieticketapp.data.remote.dto.TrailerResponseDTO
import com.soe.movieticketapp.data.remote.dto.WatchProviderResponse
import com.soe.movieticketapp.domain.model.Movie
import com.soe.movieticketapp.domain.model.Search
import com.soe.movieticketapp.domain.repository.MovieRepository
import com.soe.movieticketapp.util.MovieType
import com.soe.movieticketapp.util.Resource
import kotlinx.coroutines.flow.Flow

data class MovieUseCase(
    val getNowPlayingMovies: GetNowPlayingMovies,
    val getTrendingMovies: GetTrendingMovies,
    val getUpcomingMovies: GetUpcomingMovies,
    val getTopRatedMovies: GetTopRatedMovies,
    val getPopularMovies: GetPopularMovies,

    val getCastAndCrewMovies: GetCastAndCrewMovies,
    val getGenreMovies: GetGenreMovies,
    val getSimilarMoviesAndTvSeries: GetSimilarMoviesAndTvSeries,
    val getWatchProviders: GetWatchProviders,
    val getTrailersMovie: GetTrailersMovie,
    val getDetailMovies: GetDetailMovies,
    val getSearchMovies: GetSearchMovies,
)


class GetTrendingMovies(
    private val repository: MovieRepository
) {
    operator fun invoke(movieType: MovieType): Flow<PagingData<Movie>> {
        return repository.getTrendingMovies(movieType)
    }
}


class GetNowPlayingMovies(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(): Flow<PagingData<Movie>> {
        return repository.getNowPlayingMovies()
    }
}


class GetSimilarMoviesAndTvSeries(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(movieType: MovieType, movieId: Int): Flow<PagingData<Movie>> {
        return repository.getSimilarMoviesAndTvSeries(movieType, movieId)
    }
}


class GetUpcomingMovies(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(movieType: MovieType): Flow<PagingData<Movie>> {
        return repository.getUpcomingMovies(movieType)
    }
}

class GetTopRatedMovies(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(movieType: MovieType): Flow<PagingData<Movie>> {
        return repository.getTopRatedMovies(movieType)
    }
}

class GetPopularMovies(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(movieType: MovieType): Flow<PagingData<Movie>> {
        return repository.getPopularMovies(movieType)
    }
}

class GetSearchMovies(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(query: String): Flow<PagingData<Search>> {
        return repository.getSearchMovies(query = query)
    }
}


class GetGenreMovies(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(movieType: MovieType): Resource<GenresResponseDTO> {
        return repository.getGenreMovies(movieType)
    }
}


class GetCastAndCrewMovies(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int, movieType: MovieType): Resource<CastResponseDTO> {
        return repository.getCastAndCrewMovies(movieId = movieId, movieType = movieType)
    }
}

class GetWatchProviders(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(
        movieId: Int,
        movieType: MovieType
    ): Resource<WatchProviderResponse> {
        return repository.getMovieProviders(movieType = movieType, movieId = movieId)
    }
}


class GetTrailersMovie(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(
        movieId: Int,
        movieType: MovieType
    ): Resource<TrailerResponseDTO> {
        return repository.getTrailerMovies(movieType = movieType, movieId = movieId)
    }
}

class GetDetailMovies(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(
        movieId: Int,
        movieType: MovieType
    ): Resource<DetailResponseDTO> {
        return repository.getDetailMovies(movieType = movieType, movieId = movieId)
    }
}



