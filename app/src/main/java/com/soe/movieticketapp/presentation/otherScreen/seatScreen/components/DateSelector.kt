package com.soe.movieticketapp.presentation.otherScreen.seatScreen.components

import android.app.DatePickerDialog
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.soe.movieticketapp.presentation.common.TitleText
import com.soe.movieticketapp.util.Padding
import com.soe.movieticketapp.util.ui.theme.MovieIcons
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun DateSelector(
    modifier: Modifier = Modifier,
    onDateSelected: (String) -> Unit
) {
    // Get today's date
    val today = SimpleDateFormat("MMMM dd, EEEE", Locale.getDefault()).format(Date())

    val context = LocalContext.current

    // State to hold the selected date, defaulting to today's date
    var selectedDate by remember { mutableStateOf("Date : $today") }

    // Function to show DatePickerDialog
    val showDatePicker: () -> Unit = {
        val calendar = Calendar.getInstance()
        val datePicker = DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                // Update selectedDate with the chosen date in the desired format
                val calendarSelected = Calendar.getInstance()
                calendarSelected.set(year, month, dayOfMonth)
                val format = SimpleDateFormat("MMMM dd, EEEE", Locale.getDefault())
                selectedDate = format.format(calendarSelected.time)

                onDateSelected(selectedDate)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePicker.show()
    }

    // Row to display date and calendar icon
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(Padding.Large),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Display selected date/time
        TitleText(
            text = selectedDate,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Normal)
        )


        // Calendar icon for date selection
        IconButton(
            onClick = { showDatePicker() },
            modifier = Modifier.alignByBaseline()
        ) {
            Icon(
                painter = painterResource(id = MovieIcons.calendar), // Replace with your icon resource
                contentDescription = "Select Date",
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DateSelectorPreview() {
    MaterialTheme {
        DateSelector(
            onDateSelected = {}
        )
    }
}
