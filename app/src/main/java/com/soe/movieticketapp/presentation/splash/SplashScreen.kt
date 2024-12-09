package com.soe.movieticketapp.presentation.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.soe.movieticketapp.R
import com.soe.movieticketapp.presentation.common.TitleText
import com.soe.movieticketapp.util.ui.theme.MovieTicketAppTheme
import com.soe.movieticketapp.util.ui.theme.blue10
import kotlinx.coroutines.delay


private const val SPLASH_TIMEOUT = 2000L


@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    openAndPopUp : (String, String) -> Unit,
    viewModel: SplashScreenViewModel = hiltViewModel(),
) {

    val image = if (isSystemInDarkTheme()){
        painterResource(R.drawable.splash_screen_background)
    }else{
        painterResource(R.drawable.splash_screen_light)
    }
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ){
        Image(
            modifier = Modifier.fillMaxSize().align(Alignment.Center),
            painter = image,
            contentDescription = "splash_screen"
        )

        Column(
            modifier = Modifier.fillMaxWidth()
                .align(Alignment.Center),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier.width(130.dp).height(60.dp),
                painter = if (isSystemInDarkTheme())painterResource(R.drawable.splash_screen_logo) else painterResource(R.drawable.mupi_logo_splash),
                contentDescription = "splash_screen"
            )
            Spacer(Modifier.height(20.dp))
            TitleText(
                text = stringResource(R.string.splash_screen_text),
                color = if(isSystemInDarkTheme()) MaterialTheme.colorScheme.onBackground else blue10
            )
        }

    }



    LaunchedEffect(Unit) {
        delay(SPLASH_TIMEOUT)
        viewModel.onAppStart(openAndPopUp)
    }

}


@Preview
@Composable
private fun SplashScreenPreview() {

    MovieTicketAppTheme {
        SplashScreen(openAndPopUp = { _, _, ->})
    }

}