package com.soe.movieticketapp.presentation.detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.soe.movieticketapp.R
import com.soe.movieticketapp.domain.model.Crew
import com.soe.movieticketapp.presentation.common.AvatarsImage
import com.soe.movieticketapp.presentation.common.BodyText
import com.soe.movieticketapp.util.FontSize
import com.soe.movieticketapp.util.Padding
import com.soe.movieticketapp.util.Size
import com.soe.movieticketapp.util.ui.theme.MovieTicketAppTheme



@Composable
fun CrewInfo(
    modifier: Modifier = Modifier,
    crew : Crew
) {

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
                imageUrl = "https://image.tmdb.org/t/p/w500/${crew.profilePath}",
                context = LocalContext.current,
                contextDescription = stringResource(R.string.cast_image),
            )
        }


        val namePart = crew.name.split(" ")
        val firstName = namePart.getOrNull(0) ?: ""
        val lastName = namePart.getOrNull(1) ?: ""



        //First Name and Last Name
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
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

            // Crew
            BodyText(
                text = crew.job,
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
private fun MovieCrewPreview() {
    MovieTicketAppTheme {
        CrewInfo(
            crew = Crew(
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
        )
    }
    
}