package com.soe.movieticketapp.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@SuppressLint("NewApi")
@Composable
fun RootNavGraph(
    movieNavController: MovieNavController
) {
    NavHost(
        modifier = Modifier,
        navController = movieNavController.navController,
        startDestination = ScreenRoute.TopLevelScreenRoute.route,
    ){
        composable(route = ScreenRoute.TopLevelScreenRoute.route){
            TopLevelScreenGraph()
        }
    }

}