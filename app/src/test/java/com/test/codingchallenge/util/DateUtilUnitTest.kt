package com.test.codingchallenge.util

import org.junit.Test

import org.junit.Assert.*
import java.text.SimpleDateFormat
import java.util.Date

class DateUtilUnitTest {
    @Test
    fun calculateAge_isCorrect() {
        assertEquals(35, DateUtil.calculateAge("03/21/1989"))
    }

    @Test
    fun calculateAge_lessThanYear() {
        val dateFormatter = SimpleDateFormat("MM/dd/yyyy")
        assertEquals(0, DateUtil.calculateAge(dateFormatter.format(Date())))
    }
}
