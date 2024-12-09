package com.soe.movieticketapp.presentation.otherScreen.seatScreen.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.soe.movieticketapp.util.Padding
import com.soe.movieticketapp.util.ui.theme.MovieTicketAppTheme



@Composable
fun ShowTime(
    modifier: Modifier = Modifier,
    time: String,
    selected : Boolean,
) {

    // Box to display the time
    Box(
        modifier = modifier.padding(start = Padding.Medium),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = time,
            textAlign = TextAlign.Center,
            style = androidx.compose.ui.text.TextStyle(
                fontSize = if (selected) 20.sp else 14.sp,
                color = if (selected) MaterialTheme.colorScheme.onBackground else Color.Gray.copy(alpha = 0.6f) // Highlight center item
            ),
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun ShowTimePreview() {


    MovieTicketAppTheme {
        ShowTime(
            time = "9:00",
            selected = true


        )
    }
    
}