package com.soe.movieticketapp.presentation.home.component

import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.soe.movieticketapp.presentation.home.HomeViewModel
import com.soe.movieticketapp.util.FontSize
import com.soe.movieticketapp.util.Padding
import com.soe.movieticketapp.util.Size
import com.soe.movieticketapp.util.ui.theme.MovieTicketAppTheme
import com.soe.movieticketapp.util.ui.theme.blue06
import com.soe.movieticketapp.util.ui.theme.blue07


@Composable
fun GenreSelectContent(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = hiltViewModel()
) {

    val genres = homeViewModel.currentCategorySate
    val selectedGenre = homeViewModel.selectedCategory.value.name

    // Log the genres to ensure they're loaded correctly
    Log.d("GenreSelectContent", "Genres count: ${genres.size}")
    genres.forEach { genre ->
        Log.d("GenreSelectContent", "Genre: ${genre.name}")
    }

    // Remember LazyListState for the LazyRow to retain scroll position
    val listState = rememberLazyListState()

    // Find the selected genre's index to scroll to it when selected
    val selectedIndex = genres.indexOfFirst { it.name == selectedGenre }

    // Use LaunchedEffect to scroll to the selected item when it changes
//    LaunchedEffect(selectedIndex) {
//        if (selectedIndex >= 0){
//            listState.animateScrollToItem(index = selectedIndex)
//        }
//    }


    LazyRow(
        state = listState,
        modifier = modifier
            .fillMaxWidth()
            .padding()
            .background(MaterialTheme.colorScheme.background),
    ) {
        items(count = genres.size) { index ->
//            Timber.tag("Genres TAG").d("Displaying genre: ${genres[it].name}")
            GenreChip(
                modifier = modifier,
                genre = genres[index].name,
                isSelected = genres[index].name == selectedGenre,
                onClick = {
                    if (homeViewModel.selectedCategory.value.name != genres[index].name) {
                        homeViewModel.selectedCategory.value = genres[index]
                        homeViewModel.filterBySelectedGenre(genre = genres[index])
                    }
                }
            )
        }
    }
}


@Composable
fun GenreChip(
    modifier: Modifier = Modifier,
    genre: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {

    val selectedColor = if (isSystemInDarkTheme()){
        MaterialTheme.colorScheme.background
    }else{
        blue07
    }

    val animateChipBackgroundColor by animateColorAsState(
        targetValue = if (isSelected) selectedColor else blue06.copy(alpha = 0.2F),
        animationSpec = tween(
            durationMillis = if (isSelected) 100 else 50,
            delayMillis = 0,
            easing = LinearOutSlowInEasing
        ), label = ""
    )

    Box(
        modifier = modifier
            .padding(horizontal = Padding.Small)
            .padding(bottom = Padding.Medium)
            .widthIn(min = 60.dp)
            .height(Size.IconSizeSemiMedium)
            .clip(CircleShape)
            .background(animateChipBackgroundColor)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onClick() }
    ) {
        Text(
            text = genre,
            fontWeight = if (isSelected) FontWeight.Normal else FontWeight.Light,
            fontSize = if(isSelected) FontSize.Medium else FontSize.SemiMedium,
            style = MaterialTheme.typography.bodyMedium,
            color = if (isSelected) MaterialTheme.colorScheme.background else MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.align(Alignment.Center).padding(horizontal = Padding.Small)
        )
    }

}


@Preview(showBackground = true)
@Composable
private fun GenreSelectorPreview() {
    MovieTicketAppTheme {
        GenreChip(
            genre = "Adventure",
            isSelected = true,
            onClick = {}
        )
    }

}