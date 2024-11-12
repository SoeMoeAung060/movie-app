package com.soe.movieticketapp.navigation

import androidx.navigation.NavHostController
import com.soe.movieticketapp.domain.model.Movie

class MovieNavController(
    val navController : NavHostController
){

    fun popUp(){
        navController.popBackStack()
    }

    fun navigate(route : String){
        navController.navigate(route){
            launchSingleTop = true
        }
    }

    fun navigateAndPopUp(route: String, popUp: String){
        navController.navigate(route){
            launchSingleTop = true
            popUpTo(popUp){
                inclusive = true
            }
        }
    }

    fun clearAndNavigate(route: String) {
        navController.navigate(route) {
            launchSingleTop = true
            popUpTo(0) {
                inclusive = true
            }
        }

    }


    fun navigateToDetails(movie: Movie){
        navController.currentBackStackEntry?.savedStateHandle?.set(
            key = "movie",
            value = movie)
        navController.navigate(
            route = ScreenRoute.MovieDetailScreen.route
        )
    }


    fun navigateToCastAndCrew(movie: Movie){
        navController.currentBackStackEntry?.savedStateHandle?.set(
            key = "movie",
            value = movie)
        navController.navigate(
            route = ScreenRoute.CastAndCrewScreen.route
        )
    }






}
