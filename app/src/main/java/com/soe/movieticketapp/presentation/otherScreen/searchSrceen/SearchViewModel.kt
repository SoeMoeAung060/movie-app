package com.soe.movieticketapp.presentation.otherScreen.searchSrceen

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.soe.movieticketapp.domain.model.Search
import com.soe.movieticketapp.domain.usecase.MovieUseCase
import com.soe.movieticketapp.util.filterSearchingMovies
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val movieUseCase: MovieUseCase,
    @ApplicationContext context: Context
) : ViewModel() {


    private val prefs = context.getSharedPreferences("my_pref", Context.MODE_PRIVATE)
    // Mutable list to store search history in memory
    val searchHistory = mutableStateListOf<String>()



    private val _multiSearchState = MutableStateFlow<PagingData<Search>>(PagingData.empty())
    val multiSearchState : StateFlow<PagingData<Search>> = _multiSearchState


    val searchQuery = mutableStateOf("")
    val previousState = mutableStateOf("")

    init {
        searchQuery.value = ""
        clearSearchResults()
        loadSearchHistory()

    }

    // Save the current search history to SharedPreferences
    private fun saveSearchHistory() {
        val historySet = searchHistory.toSet()
        prefs.edit().putStringSet("search_history", historySet).apply()
    }

    // Load the search history from SharedPreferences
    private fun loadSearchHistory() {
        val historySet = prefs.getStringSet("search_history", emptySet()) ?: emptySet()
        searchHistory.clear()
        searchHistory.addAll(historySet)

    }

    // Clears the search history both in memory and in SharedPreferences
    fun clearSearchHistory(){
        searchHistory.clear()
        prefs.edit().remove("search_history").apply()
    }


    private fun addToSearchHistory(query: String) {
        if (query.isNotEmpty() && !searchHistory.contains(query)){
            searchHistory.add(0, query) // Add new query at the top of the list
            saveSearchHistory() // Save updated history to SharedPreferences
            if (searchHistory.size > 10){
                searchHistory.removeAt(searchHistory.lastIndex)
            }
        }
    }

    fun getSearchMovies(){
        viewModelScope.launch{
            val query = searchQuery.value.trim()
            if (query.isNotEmpty()) {
                Log.d("SearchViewModel", "Fetching data for query: ${searchQuery.value}")
                try {
                    addToSearchHistory(query) //// Add the query to the history
                    movieUseCase.getSearchMovies(
                        query = searchQuery.value,
                    ).map {result ->
                        result.filterSearchingMovies()
                    }.cachedIn(viewModelScope)
                        .collect{
                            _multiSearchState.value = it
                            Log.d("SearchViewModel", "Data fetched successfully: ${_multiSearchState.value}")
                        }
                }catch (e: Exception){
                    Log.e("SearchViewModel", "Error fetching data: ${e.message}")
                }
            }else{
                Log.d("SearchViewModel", "Search Query is Empty")
            }
        }
    }


    fun clearSearchResults() {
        searchQuery.value = "" // Clear the query
        _multiSearchState.value = PagingData.empty() // Reset the PagingData
    }







}
