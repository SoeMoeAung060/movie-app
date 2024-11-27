package com.soe.movieticketapp.presentation.splash

import androidx.lifecycle.ViewModel
import com.soe.movieticketapp.navigation.ScreenRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class SplashScreenViewModel @Inject constructor(
) : ViewModel(){


    fun onAppStart(openAndPopUp : (String,String) -> Unit){
        openAndPopUp(ScreenRoute.TopLevelScreenRoute.route, ScreenRoute.SplashScreen.route)

    }

}