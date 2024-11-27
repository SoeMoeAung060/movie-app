package com.soe.movieticketapp.stripePayment

import android.util.Log
import android.widget.Toast
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.fuel.json.responseJson
import com.github.kittinunf.result.Result
import com.google.gson.Gson
import com.soe.movieticketapp.domain.model.Movie
import com.soe.movieticketapp.navigation.MovieNavController
import com.soe.movieticketapp.navigation.ScreenRoute
import com.soe.movieticketapp.presentation.otherScreen.checkoutScreen.component.CheckoutButton
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
    movie: Movie
) {


    val paymentSheet = rememberPaymentSheet{ paymentSheetResult ->
        onPaymentSheetResult(
            paymentSheetResult = paymentSheetResult,
            navController = movieNavController,
            date = date,
            time = time,
            seats = seats,
            price = price,
            movie = movie
        )
    }
    val context = LocalContext.current
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
    date: String,
    time: String,
    seats: String,
    price: String,
    movie: Movie
) {

    when (paymentSheetResult) {
        is PaymentSheetResult.Canceled -> {
            Log.d("StripePayment", "Payment canceled")
        }
        is PaymentSheetResult.Failed -> {
            Log.e("StripePayment", "Payment failed: ${paymentSheetResult.error.localizedMessage}")
        }
        is PaymentSheetResult.Completed -> {
            // Serialize the movie object
            val movieJson = serializeMovieToJson(movie)
            Log.d("StripePayment", "Payment completed successfully")
            navController.navigate("ticket_screen?date=$date&time=$time&seats=${URLEncoder.encode(seats, "UTF-8")}&price=$price&movie=$movieJson")
        }
    }
}

fun serializeMovieToJson(movie: Movie): String {
    return Gson().toJson(movie)
}