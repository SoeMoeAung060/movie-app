package com.soe.movieticketapp.presentation.otherScreen.searchSrceen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.soe.movieticketapp.util.ui.theme.MovieIcons
import com.soe.movieticketapp.util.ui.theme.blue02
import com.soe.movieticketapp.util.ui.theme.neutral06
import com.soe.movieticketapp.util.ui.theme.neutral10



@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun SearchHistoryView(
    searchHistory: List<String>,
    onClearHistory: () -> Unit,
    onHistoryItemClick: (String) -> Unit,
) {

    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp, 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (searchHistory.isNotEmpty()) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp),
                    text = "Search History",
                    textAlign = TextAlign.Start,
                    color = neutral10,
                    style = MaterialTheme.typography.bodyMedium
                )
                FlowRow(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    for (record in searchHistory.reversed()) {
                        Box(
                            modifier = Modifier
                                .padding(bottom = 8.dp)
                                .clip(CircleShape)
                                .background(blue02.copy(alpha = 0.6f))
                                .padding(10.dp, 6.dp)
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null,
                                    role = Role.Button,
                                    onClick = { onHistoryItemClick(record) }
                                ),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(text = record, style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }

                Row(
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            role = Role.Button,
                            onClick = onClearHistory,
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(MovieIcons.delete),
                        contentDescription = null,
                        tint = neutral06
                    )
                    Text(
                        text = "Clear History",
                        color = neutral06,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}