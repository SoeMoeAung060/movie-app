package com.soe.movieticketapp.presentation.detail.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.soe.movieticketapp.R
import com.soe.movieticketapp.domain.model.Cast
import com.soe.movieticketapp.presentation.common.AvatarsImage
import com.soe.movieticketapp.presentation.common.BodyText
import com.soe.movieticketapp.presentation.common.IconButton
import com.soe.movieticketapp.presentation.common.LabelText
import com.soe.movieticketapp.presentation.common.TitleText
import com.soe.movieticketapp.util.Cast
import com.soe.movieticketapp.util.FontSize
import com.soe.movieticketapp.util.Padding
import com.soe.movieticketapp.util.Size
import com.soe.movieticketapp.util.ui.theme.MovieIcons
import com.soe.movieticketapp.util.ui.theme.MovieTicketAppTheme

@Composable
fun MovieCast(
    modifier: Modifier = Modifier,
    cast: List<Cast>,
    seeMoreText: String,
    onClick: () -> Unit
){
    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = Padding.Medium),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            TitleText(
                modifier = Modifier,
                fontSize = FontSize.Large,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                text = stringResource(R.string.cast_and_crew),
            )
            Row(
                modifier = Modifier.clickable { onClick() },
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                LabelText(
                    modifier = Modifier,
                    fontSize = FontSize.SemiMedium,
                    text = seeMoreText,
                )
                IconButton(
                    painter = painterResource(MovieIcons.arrowRight),
                    contentDescription = stringResource(R.string.see_more),
                    tint = MaterialTheme.colorScheme.onBackground,
                )
            }
        }

        Spacer(modifier= Modifier.height(Padding.Medium))

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items(count = minOf(cast.size, 10)) { index ->
                val castIndex = cast[index]
                CastInfo(
                    cast = castIndex
                )
            }
        }
    }
}



@Composable
fun CastInfo(
    modifier: Modifier = Modifier,
    cast: Cast
) {

    val gender = if (cast.gender == 1) "Actress" else "Actor"


    Column(
        modifier = Modifier.padding(bottom = Padding.Medium),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        //Avatar
        Box(
            modifier = Modifier
                .padding()
                .size(Size.AvatarSizeMedium),
            contentAlignment = Alignment.Center
        ) {



            AvatarsImage(
                imageUrl = "https://image.tmdb.org/t/p/w500/${cast.profilePath}",
                context = LocalContext.current,
                contextDescription = stringResource(R.string.cast_image),
            )
        }


        val namePart = cast.name.split(" ")
        val firstName = namePart.getOrNull(0) ?: ""
        val lastName = namePart.getOrNull(1) ?: ""



        //First Name and Last Name
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            BodyText(
                text = firstName,
                maxLines = 1,
                textAlign = TextAlign.Center,
                modifier = Modifier.width(Size.AvatarSizeLarge),
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                fontSize = FontSize.SemiMedium,
            )

            BodyText(
                text = lastName,
                maxLines = 1,
                textAlign = TextAlign.Center,
                modifier = Modifier.width(Size.AvatarSizeLarge),
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                fontSize = FontSize.SemiMedium,

                )

            // Actor or Actress
            BodyText(
                text = gender,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Normal),
                fontSize = FontSize.SemiMedium,
            )

        }



    }

}


@Preview(showBackground = true)
@Composable
private fun CastAndCrewPreview() {
    MovieTicketAppTheme {
        CastInfo(
            cast = Cast(
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
    }

}


@Preview(showBackground = true)
@Composable
private fun CastAndCrewUiPreview() {


    MovieTicketAppTheme {
        MovieCast(
            cast = Cast,
            seeMoreText = "See More",
            onClick = {}
        )
    }
    
}
