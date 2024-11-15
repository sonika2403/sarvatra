package com.android.sarvatra.utils

import java.util.Calendar

fun isNightTime(): Boolean {
    val calendar = Calendar.getInstance()
    val currentHour = calendar.get(Calendar.HOUR_OF_DAY)

    return currentHour >= 18 || currentHour < 4
}


fun getTimeAgo(timestamp: Long): String {
    val currentTime = System.currentTimeMillis()
    val diffInMillis = currentTime - timestamp
    val diffInSeconds = diffInMillis / 1000
    val diffInMinutes = diffInSeconds / 60
    val diffInHours = diffInMinutes / 60
    val diffInDays = diffInHours / 24
    val diffInWeeks = diffInDays / 7
    val diffInMonths = diffInDays / 30 // Approximate month length
    val diffInYears = diffInDays / 365 // Approximate year length

    return when {
        diffInSeconds < 60 -> {
            "Few seconds ago"
        }
        diffInMinutes < 60 -> {
            if (diffInMinutes == 1L) "1 minute ago" else "$diffInMinutes minutes ago"
        }
        diffInHours < 24 -> {
            if (diffInHours == 1L) "1 hour ago" else "$diffInHours hours ago"
        }
        diffInDays < 7 -> {
            if (diffInDays == 1L) "1 day ago" else "$diffInDays days ago"
        }
        diffInWeeks < 4 -> {
            if (diffInWeeks == 1L) "1 week ago" else "$diffInWeeks weeks ago"
        }
        diffInMonths < 12 -> {
            if (diffInMonths == 1L) "1 month ago" else "$diffInMonths months ago"
        }
        diffInYears >= 1 -> {
            if (diffInYears == 1L) "1 year ago" else "$diffInYears years ago"
        }
        else -> "Just now"
    }
}
