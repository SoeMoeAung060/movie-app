package com.soe.movieticketapp.presentation.home.component

import android.util.Log
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.soe.movieticketapp.R
import com.soe.movieticketapp.presentation.common.LabelText
import com.soe.movieticketapp.presentation.common.TitleText
import com.soe.movieticketapp.presentation.home.HomeViewModel
import com.soe.movieticketapp.util.FontSize
import com.soe.movieticketapp.util.MovieType
import com.soe.movieticketapp.util.Padding
import com.soe.movieticketapp.util.Size
import com.soe.movieticketapp.util.ui.theme.MovieIcons
import com.soe.movieticketapp.util.ui.theme.MovieTicketAppTheme
import kotlinx.coroutines.delay
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    navigateToSearchScreen : () ->Unit,
    homeViewModel: HomeViewModel = hiltViewModel()

) {

    TopAppBar(
        modifier = modifier,
        colors = TopAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            scrolledContainerColor = MaterialTheme.colorScheme.background,
            navigationIconContentColor = MaterialTheme.colorScheme.onBackground,
            actionIconContentColor = MaterialTheme.colorScheme.onBackground,
            titleContentColor = MaterialTheme.colorScheme.onBackground,
            ),
        title = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(Size.IconSizeLarge))
                    .background(MaterialTheme.colorScheme.onBackground.copy(0.1f))
                    .clickable { navigateToSearchScreen() }
            ) {
                CarouselText()
                Icon(
                    painter = painterResource(MovieIcons.search),
                    contentDescription = stringResource(R.string.search_movie),
                    modifier = Modifier
                        .padding(end = Padding.Medium)
                        .align(Alignment.CenterEnd),
                )
            }

        },
        actions = {


            Row(
                modifier = modifier
                    .padding(horizontal = Padding.Medium),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center

            ){

                val movieTypes = listOf(MovieType.MOVIE, MovieType.TVSHOW)
                val selectedMovieType = homeViewModel.selectedMovieType.value


                movieTypes.forEachIndexed { index, movieType ->
                    TitleText(
                        text = if (movieType == MovieType.MOVIE) "Movie" else "TV Show",
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = if(selectedMovieType == movieTypes[index]) FontSize.Large else FontSize.SemiMedium,
                        style = if(selectedMovieType == movieTypes[index])
                            MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold
                            ) else MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Light),
                        modifier = modifier
                            .padding(start = 4.dp, end = 4.dp, top = 8.dp)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ){
                                if(homeViewModel.selectedMovieType.value != movieTypes[index]){
                                    homeViewModel.selectedMovieType.value = movieTypes[index]
                                    Log.d("MovieTypeSelection", "Selected type: $movieType")
                                    Log.d("MovieTypeSelection", "Selected type: ${homeViewModel.selectedMovieType.value}")
                                    homeViewModel.getGenreMovies()
                                    homeViewModel.refreshAll()
                                }
                            },
                    )
                }
            }


        },


    )
}




@Composable
fun CarouselText(modifier: Modifier = Modifier) {

    val slides = Slide.entries.toTypedArray()
    val pagerState = rememberPagerState(
        initialPage = Random.nextInt(0, slides.size),
        initialPageOffsetFraction = 0f
    ) {
        slides.size
    }


    LaunchedEffect(Unit) {
        while (true) {
            val beforePage = pagerState.currentPage
            delay(8000)
            if (beforePage == pagerState.currentPage){
                pagerState.animateScrollToPage(
                    page = (pagerState.currentPage + 1) % slides.size,
                    pageOffsetFraction = 0f,
                    animationSpec = tween()
                )
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(Size.IconSizeMedium),
        contentAlignment = Alignment.Center
    ){
        VerticalPager(
            state = pagerState,
            modifier = modifier,
            pageSize = PageSize.Fill,
            contentPadding = PaddingValues(0.dp),
            beyondViewportPageCount = 0,
            pageSpacing = 0.dp,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            val slide = slides[it]
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
            ) {
                LabelText(
                    text = slide.title,
                    fontSize = FontSize.SemiMedium,
                    modifier = Modifier
                        .height(Size.IconSizeMedium)
                        .padding(top = Padding.Small)
                        .align(Alignment.Center)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TopBarPreview() {
    MovieTicketAppTheme {
        TopBar(
            navigateToSearchScreen = {},
        )
    }
}

@Preview(showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun TopBarDarkPreview() {
    MovieTicketAppTheme {
        TopBar(
            navigateToSearchScreen = {},
        )
    }
}