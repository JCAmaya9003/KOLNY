package com.pdm_proyecto.kolny.utils

import java.util.Calendar
import java.util.Date

fun createDate(year: Int, month: Int, day: Int): Date {
    val calendar = Calendar.getInstance()
    calendar.set(year, month - 1, day)
    return calendar.time
}