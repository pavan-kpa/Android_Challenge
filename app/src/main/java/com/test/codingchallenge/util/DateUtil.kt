package com.test.codingchallenge.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class DateUtil {
    companion object {
        fun calculateAge(dateOfBirth: String, now: Date = Date()): Int {
            if (dateOfBirth == null) return 0

            val dob = Calendar.getInstance()
            val today = Calendar.getInstance()

            val dateFormatter = SimpleDateFormat("MM/dd/yyyy")
            dob.time = dateFormatter.parse(dateOfBirth)
            today.time = now

            val year = dob[Calendar.YEAR]
            val month = dob[Calendar.MONTH]
            val day = dob[Calendar.DAY_OF_MONTH]

            dob[year, month + 1] = day

            var age = today[Calendar.YEAR] - dob[Calendar.YEAR]

            if (today[Calendar.DAY_OF_YEAR] < dob[Calendar.DAY_OF_YEAR]) {
                age--
            }

            if(age < 0) {
                age = 0
            }

            return age
        }
    }
}
