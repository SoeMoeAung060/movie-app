package com.soe.movieticketapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.soe.movieticketapp.navigation.MovieNavController
import com.soe.movieticketapp.navigation.RootNavGraph
import com.soe.movieticketapp.presentation.home.HomeScreen
import com.soe.movieticketapp.util.ui.theme.MovieTicketAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
//            statusBarStyle = SystemBarStyle.light(
//                android.graphics.Color.TRANSPARENT,
//                android.graphics.Color.TRANSPARENT
//            ),
//            navigationBarStyle = SystemBarStyle.light(
//                android.graphics.Color.TRANSPARENT,
//                android.graphics.Color.TRANSPARENT
//            )
        )
        setContent {
            MovieTicketAppTheme {
                val navController = rememberNavController()
                val movieNavController = MovieNavController(navController)
                RootNavGraph(
                    movieNavController
                )
            }
        }
    }
}
