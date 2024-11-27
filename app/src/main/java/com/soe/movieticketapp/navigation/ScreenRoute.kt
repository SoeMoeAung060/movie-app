package com.soe.movieticketapp.navigation

import com.google.gson.Gson
import com.soe.movieticketapp.domain.model.Movie

sealed class ScreenRoute(val route: String) {


    data object AuthRoute : ScreenRoute("auth_route")
    data object TopLevelScreenRoute : ScreenRoute("top_level_screen_route")

    data object SplashScreen : ScreenRoute("splash_screen")
    data object HomeScreen : ScreenRoute("home_screen")
    data object MovieListScreen : ScreenRoute("movie_list_screen")
    data object MovieDetailScreen : ScreenRoute("movie_detail_screen")
    data object SearchScreen: ScreenRoute("search_screen")
    data object WalletScreen: ScreenRoute("wallet_screen")
    data object CastAndCrewScreen : ScreenRoute("cast_and_crew_screen/{movieId}")

    data object SeatScreen : ScreenRoute("movie_seat_screen")
    data object CheckoutScreen : ScreenRoute("checkout_screen?date={date}&time={time}&seats={seats}&price={price}")
    data object TicketScreen : ScreenRoute("ticket_screen?date={date}&time={time}&seats={seats}&price={price}&movie={movie}")


}

