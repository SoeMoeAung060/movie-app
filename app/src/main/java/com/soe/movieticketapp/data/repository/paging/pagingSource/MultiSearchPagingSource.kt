package com.soe.movieticketapp.data.repository.paging.pagingSource

import android.util.Log
import androidx.datastore.core.IOException
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.soe.movieticketapp.data.remote.api.MovieApi
import com.soe.movieticketapp.domain.model.Search
import retrofit2.HttpException

class MultiSearchPagingSource(
    private val movieApi: MovieApi,
    private val query : String,
) : PagingSource<Int, Search>() {

    override fun getRefreshKey(state: PagingState<Int, Search>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Search> {
       return try {

           val page = params.key ?: 1
           val response =
               movieApi.getSearchMovies(
                   query = query,
                   page = page,
                   )

           Log.d("MultiSearchPagingSource", "load: ${response.page}")

           LoadResult.Page(
               data = response.results,
               prevKey = if (page == 1) null else page - 1,
               nextKey = if (response.results.isEmpty()) null else response.page + 1
           )


       }catch (e: IOException){
           Log.e("MultiSearchPagingSource", "load: IOException ${e.message}")
           LoadResult.Error(e)
       }catch (e: HttpException){
           Log.e("MultiSearchPagingSource", "load: httpException ${e.message}")
           LoadResult.Error(e)
       }catch (e: Exception) {
           Log.e("MultiSearchPagingSource", "load: Unknown exception ${e.message}")
           LoadResult.Error(e)
       }
    }
}