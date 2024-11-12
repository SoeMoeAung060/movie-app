//package com.soe.movieticketapp.data.repository.paging.pagingSource
//
//import androidx.paging.PagingSource
//import androidx.paging.PagingState
//import com.soe.movieticketapp.data.remote.api.MovieApi
//import com.soe.movieticketapp.domain.model.Movie
//import com.soe.movieticketapp.util.MovieType
//import retrofit2.HttpException
//import java.io.IOException
//
//class WatchProviderPagingSource(
//    private val movieApi: MovieApi,
//    private val movieId: Int,
//    private val movieType: MovieType
//) : PagingSource<Int, Movie>() {
//
//
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
//        val page = params.key ?: 1
//            return try {
//
//                val response =if (movieType == MovieType.MOVIE) movieApi.getWatchProviders(moviePath = "movie", movieId = movieId) else
//                    movieApi.getWatchProviders(moviePath = "tv", movieId = movieId)
//
//                LoadResult.Page(
//                    data = response,
//                    prevKey = if (page == 1) null else page - 1,
//                    nextKey = if (response.results) null else page + 1
//                )
//
//            } catch (e: IOException) {
//                return LoadResult.Error(e)
//            } catch (e: HttpException) {
//                return LoadResult.Error(e)
//            }
//    }
//
//    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
//        return state.anchorPosition.let { anchorPosition ->
//            state.closestPageToPosition(anchorPosition ?: return null)?.prevKey?.plus(1)
//                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
//        }
//    }
//}