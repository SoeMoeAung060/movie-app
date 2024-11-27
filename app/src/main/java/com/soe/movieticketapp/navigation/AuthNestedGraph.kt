package com.soe.movieticketapp.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.soe.movieticketapp.presentation.splash.SplashScreen


fun NavGraphBuilder.authNestedGraph(
    movieNavController: MovieNavController
){
    navigation(
        route = ScreenRoute.AuthRoute.route,
        startDestination = ScreenRoute.SplashScreen.route){

        composable(ScreenRoute.SplashScreen.route) {
            SplashScreen(openAndPopUp = { route, popUp ->
                movieNavController.navigateAndPopUp(route, popUp)
            })
        }
    }
}