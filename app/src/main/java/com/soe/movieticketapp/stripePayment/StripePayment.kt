package com.soe.movieticketapp.stripePayment

import android.util.Log
import androidx.compose.material3.Button
import androidx.compose.material3.Text
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
import com.soe.movieticketapp.presentation.otherScreen.checkoutScreen.component.CheckoutButton
import com.soe.movieticketapp.util.BACKEND_URL_ENDPOINT
import com.stripe.android.PaymentConfiguration
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult
import com.stripe.android.paymentsheet.rememberPaymentSheet
import org.json.JSONObject

@Composable
fun StripePayment(
    totalAmount : Int,
) {
    val paymentSheet = rememberPaymentSheet(::onPaymentSheetResult)
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
            .responseJson { request, response, result ->
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


private fun onPaymentSheetResult(paymentSheetResult: PaymentSheetResult) {
    when(paymentSheetResult) {
        is PaymentSheetResult.Canceled -> {
            print("Canceled")
        }
        is PaymentSheetResult.Failed -> {
            print("Error: ${paymentSheetResult.error}")
        }
        is PaymentSheetResult.Completed -> {
            // Display for example, an order confirmation screen
            print("Completed")
        }
    }
}