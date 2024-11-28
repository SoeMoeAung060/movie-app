package com.soe.movieticketapp.data.repository.paging.pagingSource

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.soe.movieticketapp.data.remote.api.MovieApi
import com.soe.movieticketapp.domain.model.Movie
import com.soe.movieticketapp.util.MovieType
import retrofit2.HttpException
import java.io.IOException

class UpcomingPagingSource(
    private val movieApi: MovieApi,
    private val movieType: MovieType,
) : PagingSource<Int, Movie>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {

        val page = params.key ?: 1
        return try {

            Log.d("UpcomingPagingSource", "Requesting page $page")
            val upcomingMovie =
                if (movieType == MovieType.MOVIE) {
                    Log.d("TrendingPagingSource", "Fetching trending")
                    movieApi.getUpcomingMovies(page = page)
                } else {
                    Log.d("TrendingPagingSource", "Fetching trending")
                    movieApi.getUpcomingTvSeries(page = page)
                }


            Log.d("TrendingPagingSource", "API response: ${upcomingMovie.results}")

            // Check if the results are empty
            if (upcomingMovie.results.isEmpty()) {
                Log.d("TrendingPagingSource", "No results found for page: $page")
            }


            LoadResult.Page(
                data = upcomingMovie.results,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (upcomingMovie.results.isEmpty()) null else upcomingMovie.page + 1
            )


        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition ?: return null)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}