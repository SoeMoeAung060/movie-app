package com.soe.movieticketapp.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.soe.movieticketapp.R
import com.soe.movieticketapp.domain.model.Movie
import com.soe.movieticketapp.presentation.common.BottomNavigationBar
import com.soe.movieticketapp.presentation.common.BottomNavigationItem
import com.soe.movieticketapp.presentation.detail.DetailScreen
import com.soe.movieticketapp.presentation.home.HomeScreen
import com.soe.movieticketapp.presentation.otherScreen.castScreen.CastAndCrewScreen
import com.soe.movieticketapp.presentation.otherScreen.movieListScreen.MovieListScreen
import com.soe.movieticketapp.presentation.otherScreen.searchSrceen.SearchScreen
import com.soe.movieticketapp.presentation.otherScreen.seatScreen.SeatScreen
import com.soe.movieticketapp.presentation.wallet.WalletScreen
import com.soe.movieticketapp.util.MovieType

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TopLevelScreenGraph(
    modifier: Modifier = Modifier
) {

    val navController = rememberNavController()
    val movieNavController = MovieNavController(navController)
    val backStackState = navController.currentBackStackEntryAsState()


    val isBottomBarVisible = remember(key1 = backStackState.value) {
        backStackState.value?.destination?.route == ScreenRoute.HomeScreen.route ||
                backStackState.value?.destination?.route == ScreenRoute.WalletScreen.route
    }

    val home = stringResource(R.string.home)
    val wallet = stringResource(R.string.wallet)

    val bottomNavigationItem = remember {
        listOf(
            BottomNavigationItem(icon = R.drawable.home, title = home),
            BottomNavigationItem(icon = R.drawable.wallet, title = wallet),
        )
    }

    var selectedItem by rememberSaveable { mutableIntStateOf(0) }

    selectedItem = remember(key1 = backStackState.value) {
        when (backStackState.value?.destination?.route) {
            ScreenRoute.HomeScreen.route -> 0
            ScreenRoute.WalletScreen.route -> 1
            else -> 0
        }
    }

    Scaffold(
        bottomBar = {
            AnimatedVisibility(
                visible = isBottomBarVisible,
                enter = slideInVertically(initialOffsetY = { it }),
                exit = slideOutVertically(targetOffsetY = { it }),
            ) {
                BottomNavigationBar(
                    items = bottomNavigationItem,
                    selectedItem = selectedItem,
                    onClick = {index ->
                        when (index) {
                            0 -> navigationToTop(
                                navController = navController,
                                route = ScreenRoute.HomeScreen.route
                            )
                            1 -> navigationToTop(
                                navController = navController,
                                route = ScreenRoute.WalletScreen.route
                            )
                        }
                    }
                )
            }
        }
    ) { paddingValues ->

        NavHost(
            modifier = modifier.padding(
                bottom = paddingValues.calculateBottomPadding()
            ),
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

            composable(ScreenRoute.WalletScreen.route) {
                WalletScreen()
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

                        },
                        navigateToSeatScreen = {
                            movieNavController.navigate(ScreenRoute.SeatScreen.route)
                        }
                    )
                }

            }


            composable(ScreenRoute.SeatScreen.route){
                SeatScreen(
                    popUp = {
                        movieNavController.popUp()
                    }
                )
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

}

private fun navigationToTop(navController: NavController, route: String) {
    navController.navigate(route) {
        navController.graph.startDestinationRoute?.let { homeScreen ->
            popUpTo(homeScreen) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}


