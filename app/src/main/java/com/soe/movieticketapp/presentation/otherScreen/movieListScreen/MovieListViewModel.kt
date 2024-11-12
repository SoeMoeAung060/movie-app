package com.soe.movieticketapp.presentation.otherScreen.movieListScreen

import androidx.lifecycle.ViewModel
import com.soe.movieticketapp.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val movieUseCase: MovieUseCase
): ViewModel() {

}