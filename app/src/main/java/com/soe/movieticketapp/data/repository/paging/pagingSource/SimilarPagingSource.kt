package com.soe.movieticketapp.data.repository.paging.pagingSource

import android.util.Log
import androidx.datastore.core.IOException
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.soe.movieticketapp.data.remote.api.MovieApi
import com.soe.movieticketapp.domain.model.Movie
import com.soe.movieticketapp.util.MovieType
import retrofit2.HttpException

class SimilarPagingSource(
    private val movieApi: MovieApi,
    private val movieId: Int,
    private val movieType: MovieType
) : PagingSource<Int, Movie>() {

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition ?: return null)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val page = params.key ?: 1
        val similarMoviesResponse = if (movieType == MovieType.MOVIE)
            movieApi.getSimilarMovies(movieId = movieId, page = page) else
                movieApi.getSimilarTvShows(tvId = movieId, page = page)

        Log.d("SimilarPagingSource", "load: ${similarMoviesResponse.results}")
        return try {
            LoadResult.Page(
                data = similarMoviesResponse.results,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (similarMoviesResponse.results.isEmpty()) null else similarMoviesResponse.page + 1
            )

        }catch (e:IOException){
            return LoadResult.Error(e)
        }catch (e : HttpException){
           return LoadResult.Error(e)
        }
    }
}