package com.soe.movieticketapp.presentation.otherScreen.checkoutScreen

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.fuel.json.responseJson
import com.github.kittinunf.result.Result
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import com.soe.movieticketapp.domain.model.Movie
import com.soe.movieticketapp.navigation.MovieNavController
import com.soe.movieticketapp.navigation.serializeMovieToJson
import com.soe.movieticketapp.presentation.otherScreen.checkoutScreen.component.CheckoutButton
import com.soe.movieticketapp.presentation.otherScreen.seatScreen.SeatScreenViewModel
import com.soe.movieticketapp.util.BACKEND_URL_ENDPOINT
import com.stripe.android.PaymentConfiguration
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult
import com.stripe.android.paymentsheet.rememberPaymentSheet
import org.json.JSONObject
import java.net.URLEncoder

@Composable
fun StripePayment(
    totalAmount : Int,
    movieNavController: MovieNavController,
    date: String,
    time: String,
    seats: String,
    price: String,
    movie: Movie,
    viewModel: SeatScreenViewModel
) {

    val context = LocalContext.current

    val paymentSheet = rememberPaymentSheet{ paymentSheetResult ->
        onPaymentSheetResult(
            paymentSheetResult = paymentSheetResult,
            navController = movieNavController,
            date = date,
            time = time,
            seats = seats,
            price = price,
            movie = movie,
            context = context,
            viewModel = viewModel
        )
    }
    var customerConfig by remember { mutableStateOf<PaymentSheet.CustomerConfiguration?>(null) }
    var paymentIntentClientSecret by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(totalAmount) {
        val payload = JSONObject().apply {
            put("amount", totalAmount)
        }

        BACKEND_URL_ENDPOINT.httpPost()
            .body(payload.toString())
            .header("Content-Type" to "application/json")
            .responseJson { request, _, result ->
                Log.d("StripePayment", "Request sent: ${request.body}")
                when (result) {
                    is Result.Success -> {
                        val responseJson = result.get().obj()
                        Log.d("StripePayment", "Response received: $responseJson")

                        // Assign values to configuration variables
                        paymentIntentClientSecret = responseJson.getString("paymentIntent")
                        customerConfig = PaymentSheet.CustomerConfiguration(
                            id = responseJson.getString("customer"),
                            ephemeralKeySecret = responseJson.getString("ephemeralKey")
                        )
                        PaymentConfiguration.init(context, responseJson.getString("publishableKey"))
                    }
                    is Result.Failure -> {
                        Log.e("StripePayment", "Failed to fetch payment configuration: ${result.error}")
                    }
                }
            }
    }

    LaunchedEffect(Unit) {
        Log.d("StripePayment", "Initialized")
    }

    CheckoutButton(
        onClick = {
            if (paymentIntentClientSecret != null && customerConfig != null) {
                Log.d("StripePayment", "Presenting PaymentSheet...")
                paymentSheet.presentWithPaymentIntent(
                    paymentIntentClientSecret!!,
                    PaymentSheet.Configuration(
                        merchantDisplayName = "My Cinema",
                        customer = customerConfig!!,
                        allowsDelayedPaymentMethods = true
                    )
                )
            } else {
                Log.e("StripePayment", "PaymentSheet configuration is null")
            }
        },
        text = "Pay Now"
    )
}

private fun onPaymentSheetResult(
    paymentSheetResult: PaymentSheetResult,
    navController: MovieNavController,
    viewModel: SeatScreenViewModel,
    context: Context,
    date: String,
    time: String,
    seats: String,
    price: String,
    movie: Movie
) {
    when (paymentSheetResult) {
        is PaymentSheetResult.Failed -> {
            Log.e("StripePayment", "Payment failed: ${paymentSheetResult.error.localizedMessage}")
            Toast.makeText(context, "Payment failed", Toast.LENGTH_SHORT).show()
        }
        is PaymentSheetResult.Canceled -> {
            Log.d("StripePayment", "Payment canceled")
            Toast.makeText(context, "Payment canceled", Toast.LENGTH_SHORT).show()
        }
        is PaymentSheetResult.Completed -> {
            Toast.makeText(context, "Payment successful!", Toast.LENGTH_SHORT).show()

            // Reserve seats in Firestore
            val seatList = seats.split(",").map { it.trim() }
            viewModel.reserveSeatsInFirestore(
                movieId = movie.id.toString(),
                showtimeId = time,
                selectedSeats = seatList,
                userId = FirebaseAuth.getInstance().currentUser?.uid ?: "Anonymous"
            )

            // Refresh seats after reservation
            viewModel.fetchSeats(movie.id.toString(), time)

            // Navigate to the ticket screen
            val movieJson = serializeMovieToJson(movie)
            navController.navigate("ticket_screen?date=$date&time=$time&seats=${URLEncoder.encode(seats, "UTF-8")}&price=$price&movie=$movieJson")
        }
    }
}

