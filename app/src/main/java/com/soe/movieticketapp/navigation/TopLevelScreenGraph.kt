package com.soe.movieticketapp.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.soe.movieticketapp.domain.model.Movie
import com.soe.movieticketapp.presentation.detail.DetailScreen
import com.soe.movieticketapp.presentation.home.HomeScreen
import com.soe.movieticketapp.presentation.otherScreen.castScreen.CastAndCrewScreen
import com.soe.movieticketapp.presentation.otherScreen.movieListScreen.MovieListScreen
import com.soe.movieticketapp.presentation.otherScreen.searchSrceen.SearchScreen
import com.soe.movieticketapp.util.MovieType

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TopLevelScreenGraph(
    modifier: Modifier = Modifier
) {

    val navController = rememberNavController()
    val movieNavController = MovieNavController(navController)

    NavHost(
        modifier = modifier,
        startDestination = ScreenRoute.HomeScreen.route,
        navController = movieNavController.navController
    ) {

        composable(ScreenRoute.HomeScreen.route) {

            HomeScreen(
                navigateToDetailScreen = {trending ->
                    movieNavController.navigateToDetails(trending)
                },
                navigateToListScreen = {
                    movieNavController.navigate(ScreenRoute.MovieListScreen.route)
                },
                navigateToSearchScreen = {
                    movieNavController.navigate(ScreenRoute.SearchScreen.route)
                }
            )
        }

        composable(ScreenRoute.MovieListScreen.route){
            MovieListScreen(
                popUp = {
                    movieNavController.popUp()
                }
            )
        }


        composable(ScreenRoute.MovieDetailScreen.route){

            val result = movieNavController.navController.previousBackStackEntry?.savedStateHandle?.get<Movie>(
                    "movie"
                )
            result?.let {
                DetailScreen(
                    movieType = MovieType.MOVIE,
                    movie = it,
                    popUp = {
                        movieNavController.popUp()
                    },
                    navigateToDetailScreen = {similarMovie ->
                        movieNavController.navigateToDetails(similarMovie)
                    },
                    navigateToCastAndCrewScreen = {movie ->
                        movieNavController.navigateToCastAndCrew(movie)

                    }
                )
            }

        }


        composable(ScreenRoute.CastAndCrewScreen.route){
            val movieId = movieNavController.navController.previousBackStackEntry?.savedStateHandle?.get<Movie>(
                "movie"
            )

            movieId?.let {
                CastAndCrewScreen(
                    popUp = {
                        movieNavController.popUp()
                    },
                    movie = it ,
                    movieType = MovieType.MOVIE
                )
            }
        }


        composable(ScreenRoute.SearchScreen.route){
            SearchScreen(
                navigateToDetailScreen = {
                    movieNavController.navigateToDetails(it)
                },
                popUp = {
                    movieNavController.popUp()
                }
            )
        }
    }

}