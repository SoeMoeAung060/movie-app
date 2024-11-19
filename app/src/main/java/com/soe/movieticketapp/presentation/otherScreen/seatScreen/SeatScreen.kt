package com.soe.movieticketapp.presentation.otherScreen.seatScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.soe.movieticketapp.presentation.otherScreen.seatScreen.components.DateSelector
import com.soe.movieticketapp.presentation.otherScreen.seatScreen.components.SeatScreenMovieTopBar


@Composable
fun SeatScreen(
    modifier: Modifier = Modifier,
    popUp: () -> Unit
) {


    Scaffold(
        topBar = {
            SeatScreenMovieTopBar(
                onBackClick = popUp
            )
        }
    ) {
        Box(
            modifier = modifier.padding(it)
        ){
            DateSelector()


        }
    }
}