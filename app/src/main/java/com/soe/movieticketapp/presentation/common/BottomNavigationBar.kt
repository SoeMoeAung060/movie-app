package com.soe.movieticketapp.presentation.common

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.soe.movieticketapp.util.BottomNavigationSize


data class BottomNavigationItem(
    val title: String,
    @DrawableRes val icon: Int,
)


@Composable
fun BottomNavigationBar(
    items: List<BottomNavigationItem>,
    selectedItem : Int,
    onClick : (Int) -> Unit,) {

    NavigationBar(
        modifier = Modifier.fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)),
        tonalElevation = 5.dp,
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = index == selectedItem,
                onClick = { onClick(index) },
                icon = {
                    Column(
                        modifier = Modifier.height(BottomNavigationSize.Height),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            painter = painterResource(id = item.icon),
                            contentDescription = null
                        )
                        if (index == selectedItem) {
                            Text(text = item.title, style = MaterialTheme.typography.labelSmall)
                        }
                    }

                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.tertiary,
                    unselectedTextColor = MaterialTheme.colorScheme.tertiary,
                    indicatorColor = MaterialTheme.colorScheme.background
                )
            )
        }
    }

}