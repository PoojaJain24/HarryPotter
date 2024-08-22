package com.example.utilities

import java.text.SimpleDateFormat
import java.util.Locale

object DateUtil {
    fun formatDateDDMMMYYYY(dateString: String): String? {
        val parser = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        try {
            val formatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
            return parser.parse(dateString)?.let { formatter.format(it) }
        }catch(e:java.text.ParseException){
            e.printStackTrace()
        }
        return "Unknown"
    }
}