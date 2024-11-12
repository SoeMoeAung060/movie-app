package com.soe.movieticketapp.presentation.common

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import com.soe.movieticketapp.util.FontSize
import com.soe.movieticketapp.util.Padding
import com.soe.movieticketapp.util.formatRating
import com.soe.movieticketapp.util.ui.theme.MovieTicketAppTheme

@Composable
fun HeaderMovieText(
    modifier: Modifier = Modifier,
    text : String
) {
    Text(
        modifier = modifier,
        text = text,
        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold),
        fontSize = FontSize.ExtraLarge,
        color = MaterialTheme.colorScheme.onBackground
    )
}


@Composable
fun LabelText(
    modifier: Modifier = Modifier,
    text : String,
    fontSize: TextUnit = FontSize.Small,
    color : Color = MaterialTheme.colorScheme.onBackground,
    textAlign: TextAlign = TextAlign.Left
) {
    Text(
        modifier = modifier,
        text = text,
        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Normal),
        fontSize = fontSize,
        color = color,
        textAlign = textAlign
    )
    
}

@Composable
fun TitleListText(
    modifier: Modifier = Modifier,
    title: String,
    fontSize: TextUnit = FontSize.Medium,
    style : TextStyle = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Medium),
    color: Color = MaterialTheme.colorScheme.onBackground
) {
    val displayedTitle = if (title.length > 24) {
        title.take(24) + "..."
    } else {
        title
    }
    Text(
        modifier = modifier,
        text = displayedTitle,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        style = style,
        fontSize = fontSize,
        color = color
    )
}

@Composable
fun TitleText(
    modifier: Modifier = Modifier,
    text: String,
    fontSize: TextUnit = FontSize.Medium,
    style : TextStyle = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Medium),
    color: Color = MaterialTheme.colorScheme.onBackground
) {

    Text(
        modifier = modifier,
        text = text,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        style = style,
        fontSize = fontSize,
        color = color
    )
}




@Composable
fun SubTitleText(
    modifier: Modifier = Modifier,
    text: String,
    fontSize: TextUnit = FontSize.Small,
    style : TextStyle = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Normal),
    color: Color = MaterialTheme.colorScheme.onBackground
) {
    Text(
        modifier = modifier,
        text = text,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        style = style,
        fontSize = fontSize,
        color = color
    )
}



@Composable
fun BodyText(
    modifier: Modifier = Modifier,
    text: String,
    maxLines: Int = 3,
    fontSize: TextUnit = FontSize.Small,
    style : TextStyle = MaterialTheme.typography.bodyMedium,
    color: Color = MaterialTheme.colorScheme.onBackground,
    textAlign: TextAlign = TextAlign.Left
) {


    Text(
        modifier = modifier,
        text = text,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis,
        style = style,
        fontSize = fontSize,
        color = color,
        textAlign = textAlign
    )
}

@Composable
fun RatingText(
    modifier: Modifier = Modifier,
    rating : Double?,
    fontSize: TextUnit = FontSize.Small,
    style : TextStyle = MaterialTheme.typography.labelMedium,
    color: Color = if(isSystemInDarkTheme()) Color.DarkGray else Color.Yellow
) {
    val formatRating = formatRating(rating)

    Text(
        modifier = modifier.padding(horizontal = Padding.ExtraSmall),
        text = formatRating,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        style = style,
        fontSize = fontSize,
        color = color
    )
}

@Composable
fun HotText(
    modifier: Modifier = Modifier,
    text: String,
    fontSize: TextUnit = FontSize.Small,
    style : TextStyle = MaterialTheme.typography.titleSmall,
    color: Color = if(isSystemInDarkTheme()) Color.Yellow else Color.DarkGray
) {
    Text(
        modifier = modifier,
        text = text,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        style = style,
        fontSize = fontSize,
        color = color
    )
}



@Preview(showBackground = true)
@Composable
private fun BodyTextPreview() {
    MovieTicketAppTheme {
        BodyText(
            modifier = Modifier.fillMaxWidth(),
            text = "Spider-Man: No Way Home is a 2021 American superhero film based on the Marvel Comics character of the same name. Produced by Columbia Pictures, Marvel Studios, and Walt Disney Studios Motion Pictures,")
    }

    
}

@Preview(showBackground = true)
@Composable
private fun MovieTitleTextPreview() {
    MovieTicketAppTheme {
        TitleText(text = "Spider-Man: No Way Home")
    }
}


@Preview(showBackground = true)
@Composable
private fun HeaderTextMoviePreview() {
    MovieTicketAppTheme {
        HeaderMovieText(text = "Now Showing")
    }
}

@Preview(showBackground = true)
@Composable
private fun SeeMoreMoviePreview() {
    MovieTicketAppTheme {
        LabelText(
            text = "See More",
//            onClickSeeMore = {}
        )
    }
}