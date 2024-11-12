package com.soe.movieticketapp.presentation.home.component

import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.soe.movieticketapp.domain.model.Genre
import com.soe.movieticketapp.domain.model.Movie
import com.soe.movieticketapp.presentation.common.TitleListText
import com.soe.movieticketapp.presentation.common.TitleText
import com.soe.movieticketapp.util.Banner
import com.soe.movieticketapp.util.FontSize
import com.soe.movieticketapp.util.Padding
import com.soe.movieticketapp.util.ui.theme.MovieTicketAppTheme
import com.soe.movieticketapp.util.ui.theme.basicWhite
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun Carousel(
    modifier: Modifier = Modifier,
    movie: Movie,
    onClick: (Movie) -> Unit,
) {

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
            delay(5000)
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
        modifier = Modifier.fillMaxWidth()
            .height(Banner.BannerHeight)
            .clickable { onClick(movie) }
    ){
        HorizontalPager(
            state = pagerState,
            modifier = modifier,
            pageSize = PageSize.Fill,
            contentPadding = PaddingValues(0.dp),
            beyondViewportPageCount = 0,
            pageSpacing = 0.dp,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val slide = slides[it]
            Box(
            ){
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(slide.imageId),
                    contentDescription = slide.title,
                    contentScale = ContentScale.Crop
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.BottomStart)
                        .height(64.dp)
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color.Black.copy(0.9f)
                                )
                            )
                        ),
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomStart)
                        .padding(start = Padding.Medium, bottom = Padding.Medium)
                ) {
                    TitleText(
                        text = slide.title,
                        color = Color.White,
                        fontSize = FontSize.Large
                    )
                    TitleListText(
                        title = slide.overview,
                        color = Color.White,
                        fontSize = FontSize.Small,
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Light)
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = Padding.Medium, bottom = Padding.Medium),
            horizontalArrangement = Arrangement.spacedBy(2.dp),
        ) {
            repeat(slides.size){ page ->
                Box(
                    Modifier
                        .size(6.dp)
                        .padding(if (pagerState.currentPage == page) 0.dp else 1.dp)
                        .background(
                            color = if (pagerState.currentPage == page)
                                basicWhite.copy(1f) else basicWhite.copy(0.7f),
                            shape = CircleShape
                        )
                )
            }

        }
    }

}


@Preview
@Composable
private fun CarouselPreview() {
    MovieTicketAppTheme {
        Carousel(
            onClick = {},
            movie = Movie(
                adult = false,
                backdropPath = "/path/to/backdrop2.jpg",
                posterPath = "/path/to/poster2.jpg",
                genreIds = listOf(16, 35),
                genres = listOf(Genre(3, "Animation"), Genre(4, "Comedy")),
                mediaType = "movie",
                firstAirDate = "2023-02-02",
                id = 2,
                imdbId = "tt7654321",
                originalLanguage = "en",
                originalName = "Original Name 2",
                overview = "This is the overview of the second movie.",
                popularity = 9.0,
                releaseDate = "2023-02-02",
                runtime = 90,
                title = "Movie Title 2",
                video = true,
                voteAverage = 8.0,
                voteCount = 200
            )
        )
    }

}