package com.soe.movieticketapp.data.repository.paging.pagingSource

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.soe.movieticketapp.data.remote.api.MovieApi
import com.soe.movieticketapp.domain.model.Movie
import com.soe.movieticketapp.util.MovieType
import retrofit2.HttpException
import java.io.IOException

class NowPlayingPagingSource(
    private val movieApi: MovieApi,
) : PagingSource<Int, Movie>() {

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition ?: return null)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {


        val page = params.key ?: 1
        return try {

            Log.d("NowPlayingMoviesPagingSource", "Requesting page $page")
            val nowPlayingMovie = movieApi.getNowPlayingMovies(page = page)



            Log.d("NowPlayingMoviesPagingSource", "API response: ${nowPlayingMovie.results}")

            // Check if the results are empty
            if (nowPlayingMovie.results.isEmpty()) {
                Log.d("NowPlayingMoviesPagingSource", "No results found for page: $page")
            }


            LoadResult.Page(
                data = nowPlayingMovie.results,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (nowPlayingMovie.results.isEmpty()) null else nowPlayingMovie.page + 1
            )


        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        }
    }
}