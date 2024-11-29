package com.soe.movieticketapp.util

import android.content.Context
import java.util.UUID

fun getOrCreateUserId(context: Context): String {
    val sharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
    var userId = sharedPreferences.getString("USER_ID", null)

    // Generate a new UUID if no user ID exists
    if (userId == null) {
        userId = UUID.randomUUID().toString()
        sharedPreferences.edit().putString("USER_ID", userId).apply()
    }

    return userId
}
