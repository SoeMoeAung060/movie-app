@file:Suppress("NAME_SHADOWING")

package com.soe.movieticketapp.presentation.otherScreen.castScreen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.soe.movieticketapp.R
import com.soe.movieticketapp.domain.model.Cast
import com.soe.movieticketapp.domain.model.Crew
import com.soe.movieticketapp.domain.model.Movie
import com.soe.movieticketapp.presentation.detail.DetailScreenViewModel
import com.soe.movieticketapp.presentation.detail.components.CastInfo
import com.soe.movieticketapp.presentation.detail.components.CrewInfo
import com.soe.movieticketapp.util.MovieType
import com.soe.movieticketapp.util.Padding
import com.soe.movieticketapp.util.TopBarHeaderTitle
import com.soe.movieticketapp.util.ui.theme.MovieTicketAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CastAndCrewScreen(
    modifier: Modifier = Modifier,
    movie: Movie,
    movieType: MovieType,
    popUp: () -> Unit,
    castAndCrewViewModel: CastAndCrewViewModel = hiltViewModel()
) {

    val movie by remember { mutableStateOf(movie) }
    val movieType = remember { movieType }

    val getCast = castAndCrewViewModel.castState.value
    val getCrew = castAndCrewViewModel.crewState.value

    // Log to check the values
    Log.d("CastAndCrewScreen", "Cast: ${getCast.size}, Crew: ${getCrew.size}")

    // Fetch data when the screen is displayed
    LaunchedEffect(movie.id, movieType) {
        castAndCrewViewModel.fetchCastAndCrew(movie.id, movieType)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = { popUp() },
                title = {
                    TopBarHeaderTitle(
                        headerText = stringResource(R.string.cast_and_crew)
                    )
                }
            )
        }
    ) {
        Box(modifier = modifier
            .padding(it)
            .fillMaxWidth()){
            MovieCastAndCrew(
                modifier = modifier
                    .padding()
                    .align(Alignment.Center),
                crew = getCrew,
                cast = getCast
            )
        }
    }

}

@Composable
fun MovieCastAndCrew(
    modifier: Modifier = Modifier,
    crew: List<Crew>,
    cast: List<Cast>
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        if (cast.isNotEmpty()) {
            Text(
                text = stringResource(R.string.cast),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(Padding.Small)
            )
            LazyVerticalGrid(
                modifier = Modifier.padding(),
                columns = GridCells.Fixed(4),
                horizontalArrangement = Arrangement.Center,
                verticalArrangement = Arrangement.Center
            ) {
                items(cast.size) { index ->
                    CastInfo(
                        modifier = Modifier.padding(Padding.Small),
                        cast = cast[index]
                    )
                }
            }
        } else {
            Text(text = stringResource(R.string.no_cast), style = MaterialTheme.typography.bodyLarge)
        }

        Spacer(modifier = Modifier.height(Padding.Medium))

        if (crew.isNotEmpty()) {
            Text(
                text = stringResource(R.string.crew),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(Padding.Small)
            )
            LazyVerticalGrid(
                modifier = Modifier.padding(),
                columns = GridCells.Fixed(4),
                horizontalArrangement = Arrangement.Center,
                verticalArrangement = Arrangement.Center
            ) {
                items(crew.size) { index ->
                    CrewInfo(
                        modifier = Modifier.padding(Padding.Small),
                        crew = crew[index]
                    )
                }
            }
        } else {
            Text(text = stringResource(R.string.no_crew), style = MaterialTheme.typography.bodyLarge)
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun CastAndCrewScreenPreview() {
    MovieTicketAppTheme {
        MovieCastAndCrew(
            crew = listOf(
                Crew(
                    adult = false,
                    creditId = "52fe44f2c3a36847f80b36c7",
                    department = "Directing",
                    gender = 2, // 1 for female, 2 for male, 0 for gender non-specific
                    id = 3048,
                    job = "Director",
                    knownForDepartment = "Directing",
                    name = "Peter Best",
                    originalName = "Peter Best",
                    popularity = 0.606,
                    profilePath = "/zNb59bd0Ye0TeSjkQDtqajNxtVe"
                ),
                Crew(
                    adult = false,
                    creditId = "52fe44f2c3a36847f80b36c7",
                    department = "Directing",
                    gender = 2, // 1 for female, 2 for male, 0 for gender non-specific
                    id = 3048,
                    job = "Director",
                    knownForDepartment = "Directing",
                    name = "Peter Best",
                    originalName = "Peter Best",
                    popularity = 0.606,
                    profilePath = "/zNb59bd0Ye0TeSjkQDtqajNxtVe"
                ),
                Crew(
                    adult = false,
                    creditId = "52fe44f2c3a36847f80b36c7",
                    department = "Directing",
                    gender = 2, // 1 for female, 2 for male, 0 for gender non-specific
                    id = 3048,
                    job = "Director",
                    knownForDepartment = "Directing",
                    name = "Peter Best",
                    originalName = "Peter Best",
                    popularity = 0.606,
                    profilePath = "/zNb59bd0Ye0TeSjkQDtqajNxtVe"
                )
            ),
            cast = listOf(
                Cast(
                    adult = false,
                    gender = 1,
                    id = 2342,
                    department = "Acting",
                    name = "John Meillon",
                    originalName = "John Meillon",
                    popularity = 3.385,
                    profilePath = "/k9x2VwKqiTxTKP1WIgOM1AtZf2G.jpg",
                    castId = 3,
                    character = "Walter Reilly",
                    creditId = "52fe44f2c3a36847f80b3693",
                    order = 2
                )
            )
        )
    }

}
