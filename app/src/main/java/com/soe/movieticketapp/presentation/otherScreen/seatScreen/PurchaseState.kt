package com.soe.movieticketapp.presentation.otherScreen.seatScreen

sealed class PurchaseState {

    data object Idle : PurchaseState()
    data object Loading : PurchaseState()
    data class Success(val ticketDetail: TicketDetail) : PurchaseState()
    data class Error(val message: String) : PurchaseState()

}

data class TicketDetail(
    val seats : String,
    val time : String,
    val total : Int
)