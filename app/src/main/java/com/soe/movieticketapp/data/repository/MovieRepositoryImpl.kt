package com.soe.movieticketapp.data.repository

import android.util.Log
import androidx.datastore.core.IOException
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.soe.movieticketapp.data.remote.api.MovieApi
import com.soe.movieticketapp.data.remote.dto.CastResponseDTO
import com.soe.movieticketapp.data.remote.dto.DetailResponseDTO
import com.soe.movieticketapp.data.remote.dto.GenresResponseDTO
import com.soe.movieticketapp.data.remote.dto.TrailerResponseDTO
import com.soe.movieticketapp.data.remote.dto.WatchProviderResponse
import com.soe.movieticketapp.data.repository.paging.pagingSource.MultiSearchPagingSource
import com.soe.movieticketapp.data.repository.paging.pagingSource.NowPlayingPagingSource
import com.soe.movieticketapp.data.repository.paging.pagingSource.SimilarPagingSource
import com.soe.movieticketapp.data.repository.paging.pagingSource.TrendingPagingSource
import com.soe.movieticketapp.domain.model.Movie
import com.soe.movieticketapp.domain.model.Search
import com.soe.movieticketapp.domain.repository.MovieRepository
import com.soe.movieticketapp.util.MovieType
import com.soe.movieticketapp.util.PAGE_SIZE
import com.soe.movieticketapp.util.Resource
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

@Suppress("UNREACHABLE_CODE")
class MovieRepositoryImpl @Inject constructor(
    private val movieApi: MovieApi,
) : MovieRepository {

    override fun getTrendingMovies(movieType: MovieType): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            pagingSourceFactory = {
                TrendingPagingSource(movieApi = movieApi, movieType = movieType)
            }
        ).flow
    }


    override suspend fun getSimilarMoviesAndTvSeries(
        movieType: MovieType,
        movieId: Int
    ): Flow<PagingData<Movie>> {
        Log.d("GetSimilarMovies", "getSimilarMovies:$movieId")
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            pagingSourceFactory = {
                SimilarPagingSource(
                    movieApi = movieApi,
                    movieType = movieType,
                    movieId = movieId
                )
            }
        ).flow
    }

    override suspend fun getSearchMovies(
        query: String
    ): Flow<PagingData<Search>> {

        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            pagingSourceFactory = {
                MultiSearchPagingSource(
                    query = query,
                    movieApi = movieApi
                )
            }
        ).flow
    }

    override suspend fun getNowPlayingMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            pagingSourceFactory = {
                NowPlayingPagingSource(
                    movieApi = movieApi
                )
            }
        ).flow
    }


    /** No Paging Source */

    override suspend fun getCastAndCrewMovies(
        movieType: MovieType,
        movieId: Int
    ): Resource<CastResponseDTO> {
        return try {
            Log.d("GetCastMovies", "getCastMovies: $movieId")

            val response = if (movieType == MovieType.MOVIE) {
                movieApi.getMoviesCastAndCrew(movieId = movieId)
            } else {
                movieApi.getTvShowCastAndCrew(movieId = movieId)
            }

            Log.d("GetCastMovies", "getCastMovies Response: $response")


            if (response.castResult.isEmpty()) {
                Log.e("GetCastMovies", "No cast Result Found")
                return Resource.Error("No cast Result")
            }
            // Log the size of the cast result
            Log.d("GetCastMovies", "Cast size: ${response.castResult.size}")

            return Resource.Success(response)

        } catch (e: CancellationException) {
            Log.e("GetCastMovies", "Job was cancelled: ${e.message}")
            throw e // Let the coroutine framework handle cancellation properly
        } catch (e: Exception) {
            Log.e("GetCastMovies", "Error fetching cast: ${e.message}")
            return Resource.Error(message = e.message.toString())
        }

    }


    override suspend fun getGenreMovies(
        movieType: MovieType
    ): Resource<GenresResponseDTO> {
        val response = try {
            if (movieType == MovieType.MOVIE) {
                Log.d("GetGenreMovies", "Fetching movie genres...")
                movieApi.getMovieGenres()
            } else {
                Log.d("GetGenreMovies", "Fetching TV show genres...")
                movieApi.getTvShowGenres()
            }

        } catch (e: Exception) {
            Log.e("GetGenreMovies", "Error fetching genres: ${e.message}")
            return Resource.Error(message = "Failed to fetch genres: ${e.message}")
        }

        // Log the response to inspect the structure and content
        Log.d("GetGenreMovies", "Genres fetched for $movieType: ${response.genres.size} items")
        response.genres.forEach { genre ->
            Log.d("GetGenreMovies", "Genre ID: ${genre.id}, Name: ${genre.name}")
        }

        return Resource.Success(response)
    }


    override suspend fun getMovieProviders(
        movieType: MovieType,
        movieId: Int
    ): Resource<WatchProviderResponse> {
        val response = try {
            if (movieType == MovieType.MOVIE) movieApi.getWatchProviders(
                moviePath = "movie",
                movieId = movieId
            ) else
                movieApi.getWatchProviders(moviePath = "tv", movieId = movieId)
        } catch (e: IOException) {
            return Resource.Error(message = "Error fetching.")
        } catch (e: HttpException) {
            return Resource.Error(message = "Error")
        }

        return Resource.Success(response)
    }

    override suspend fun getTrailerMovies(
        movieType: MovieType,
        movieId: Int
    ): Resource<TrailerResponseDTO> {
        val response = try {
            if (movieType == MovieType.MOVIE)
                movieApi.getWatchTrailersMovie(movieId = movieId, moviePath = "movie")
            else movieApi.getWatchTrailersMovie(movieId = movieId, moviePath = "tv")

//            val trailer = response.results.firstOrNull { it.type == "Trailer" }
//            trailer.let { "https://www.youtube.com/watch?v=${it?.key}" }

        } catch (e: IOException) {
            return Resource.Error(message = "Error fetching.")
        } catch (e: HttpException) {
            return Resource.Error(message = "Error")

        }

        return Resource.Success(response)
    }

    override suspend fun getDetailMovies(
        movieType: MovieType,
        movieId: Int
    ): Resource<DetailResponseDTO> {
        val response = try{
            if (movieType == MovieType.MOVIE)
                movieApi.getDetailMovies(movieId = movieId, moviePath = "movie")
            else movieApi.getDetailMovies(movieId = movieId, moviePath = "tv")


        }catch (e:IOException){
            return Resource.Error("Error fetching")
        }catch (e: HttpException){
            return Resource.Error("Error")
        }

        return Resource.Success(response)
    }


}