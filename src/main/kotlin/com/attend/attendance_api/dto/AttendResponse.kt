package com.attend.attendance_api.dto

import java.time.LocalDate
import java.time.LocalDateTime

data class AttendResponse(
    val id: Long?,
    val userId: Long,
    val date: LocalDate,
    val type: String,
    val startTime: LocalDateTime?,
    val endTime: LocalDateTime?

)
