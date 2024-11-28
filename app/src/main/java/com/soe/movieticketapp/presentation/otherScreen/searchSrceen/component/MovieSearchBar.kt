package com.soe.movieticketapp.presentation.otherScreen.searchSrceen.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.soe.movieticketapp.R
import com.soe.movieticketapp.presentation.common.TitleText
import com.soe.movieticketapp.presentation.otherScreen.searchSrceen.SearchViewModel
import com.soe.movieticketapp.util.Padding
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieSearchBar(
    modifier: Modifier = Modifier,
    popUp: () -> Unit,
    onSearch: () -> Unit,
) {


    TopAppBar(
        modifier = modifier.padding(horizontal = Padding.Medium),
        title = {
            SearchBoxMovie(
                modifier = Modifier,
                onSearch = onSearch,
                autoFocus = false,
            )
        },
        actions = {
            CancelSearchIcon(
                interactionResource = remember { MutableInteractionSource() },
                onCancelPopUp = popUp,
            )
        },
        colors = TopAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            scrolledContainerColor = MaterialTheme.colorScheme.background,
            navigationIconContentColor = MaterialTheme.colorScheme.onBackground,
            actionIconContentColor = MaterialTheme.colorScheme.onBackground,
            titleContentColor = MaterialTheme.colorScheme.onBackground,
        ),
    )

}


@Composable
fun CancelSearchIcon(
    modifier: Modifier = Modifier,
    interactionResource: MutableInteractionSource = remember { MutableInteractionSource() },
    onCancelPopUp: () -> Unit,
    searchViewModel: SearchViewModel = hiltViewModel()
) {


    TitleText(
        modifier = modifier.clickable(
            interactionSource = interactionResource,
            indication = null,
            role = Role.Button,
            onClick = {
                if (searchViewModel.searchQuery.value.isNotEmpty()) {
                    // Clear the search text and results if there is an active query
                    searchViewModel.clearSearchResults()
                } else {
                    // If search query is already empty, navigate to the home screen
                    onCancelPopUp()
                }
            }

        ),
        text = "Cancel",
        color = MaterialTheme.colorScheme.onBackground,
        style = MaterialTheme.typography.bodyMedium,
    )

}


@Composable
fun SearchBoxMovie(
    autoFocus: Boolean,
    modifier: Modifier = Modifier,
    onSearch: () -> Unit,
    viewModel: SearchViewModel = hiltViewModel()

) {


    Box(
        modifier = modifier
            .padding(end = Padding.Medium)
            .fillMaxWidth()
            .clip(CircleShape),
        contentAlignment = Alignment.Center
    ) {


        var searchInput by remember { mutableStateOf("") }


        val focusRequester = remember { FocusRequester() }
        val focusManager = LocalFocusManager.current

        LaunchedEffect(key1 = searchInput) {
            if (viewModel.searchQuery.value.trim().isNotEmpty()
                && viewModel.searchQuery.value.trim().length != viewModel.previousState.value.trim().length
            ) {

                delay(750)
                onSearch()
                viewModel.previousState.value = searchInput.trim()
            }

        }

        TextField(
            modifier = modifier
                .clip(CircleShape)
                .focusRequester(focusRequester),
            value = searchInput,
            onValueChange = { newValue: String ->
                searchInput = if (newValue.trim().isNotEmpty()) newValue else ""
                viewModel.searchQuery.value = searchInput
            },
            placeholder = {
                Text(
                    text = "Search Movies",
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                    textAlign = TextAlign.Center
                )
            },
            textStyle = MaterialTheme.typography.bodySmall.copy(
                color = MaterialTheme.colorScheme.onSurface,
            ),

            trailingIcon = {
                LaunchedEffect(Unit) {
                    if (autoFocus) {
                        focusRequester.requestFocus()
                    }
                }
                Row(modifier = modifier) {
                    AnimatedVisibility(
                        visible = searchInput.trim().isNotEmpty()
                    ) {
                        IconButton(
                            onClick = {
                                focusManager.clearFocus()
                                searchInput = ""
                                viewModel.clearSearchResults()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Clear,
                                contentDescription = stringResource(R.string.close),
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }
                }
            },
            keyboardOptions = KeyboardOptions(
                autoCorrectEnabled = true,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    if (viewModel.searchQuery.value.trim().isNotEmpty()) {
                        focusManager.clearFocus()
                        viewModel.searchQuery.value = searchInput
                        if (searchInput != viewModel.previousState.value) {
                            viewModel.previousState.value = searchInput
                            onSearch()
                        }
                    }
                }
            ),

            singleLine = true,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = MaterialTheme.colorScheme.onBackground.copy(0.1f),
                focusedContainerColor = MaterialTheme.colorScheme.onBackground.copy(0.1f),
                disabledContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                focusedTextColor = MaterialTheme.colorScheme.onBackground,
                unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                cursorColor = MaterialTheme.colorScheme.onBackground,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent
            )
        )
    }


}


