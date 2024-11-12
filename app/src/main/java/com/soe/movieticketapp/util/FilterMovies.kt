package com.soe.movieticketapp.util

import androidx.paging.PagingData
import androidx.paging.filter
import androidx.paging.map
import com.soe.movieticketapp.domain.model.Movie
import com.soe.movieticketapp.domain.model.Search
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

fun PagingData<Movie>.filterTrendingMovies() : PagingData<Movie> {

    return this.filter { movie ->
        val hasValidId = movie.id > 0
        val hasValidTitle = !movie.title.isNullOrEmpty()
        val contentIsNotRemoved = movie.overview?.contains("[Removed]") == false
        val hasValidImage = !movie.posterPath.isNullOrEmpty()

        hasValidId && hasValidTitle && contentIsNotRemoved && hasValidImage

    }
}

fun PagingData<Search, >.filterSearchingMovies() : PagingData<Search> {

    return this.filter { movie ->
        val hasValidId = movie.id!! > 0
        val hasValidTitle = !movie.title.isNullOrEmpty()
        val contentIsNotRemoved = movie.overview?.contains("[Removed]") == false
        val hasValidImage = !movie.posterPath.isNullOrEmpty()
        val hasValidOverview = !movie.overview.isNullOrEmpty()


        hasValidId && hasValidTitle && contentIsNotRemoved && hasValidImage && hasValidOverview

    }
}


fun filterTrendingMovies(pagingData: PagingData<Movie>): Flow<PagingData<Movie>> = flow {
    pagingData.map { movie ->
        val hasValidId = movie.id > 0
        val hasValidTitle = !movie.title.isNullOrEmpty()
        val contentIsNotRemoved = movie.overview?.contains("[Removed]") == false
        val hasValidImage = !movie.posterPath.isNullOrEmpty()

        hasValidId && hasValidTitle && contentIsNotRemoved && hasValidImage

    }
}
