package com.soe.movieticketapp.data.repository.paging.pagingSource

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.soe.movieticketapp.data.remote.api.MovieApi
import com.soe.movieticketapp.domain.model.Movie
import com.soe.movieticketapp.util.MovieType
import retrofit2.HttpException
import java.io.IOException

class TopRatePagingSource(
    private val movieApi: MovieApi,
    private val movieType: MovieType,
) : PagingSource<Int, Movie>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {

        val page = params.key ?: 1
        return try {

            Log.d("PopularPagingSource", "Requesting page $page")
            val topRateMovie =
                if (movieType == MovieType.MOVIE) {
                    Log.d("TrendingPagingSource", "Fetching trending")
                    movieApi.getTopRatedMovies(page = page)
                } else {
                    Log.d("TrendingPagingSource", "Fetching trending")
                    movieApi.getTopRateTvSeries(page = page)
                }


            Log.d("TrendingPagingSource", "API response: ${topRateMovie.results}")

            // Check if the results are empty
            if (topRateMovie.results.isEmpty()) {
                Log.d("TrendingPagingSource", "No results found for page: $page")
            }


            LoadResult.Page(
                data = topRateMovie.results,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (topRateMovie.results.isEmpty()) null else topRateMovie.page + 1
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