package com.attend.attendance_api.dto

import java.time.LocalDateTime

data class AttendEndRequest(
    val endTime: LocalDateTime,
    val endLocation: String,
    val updatedUser: Long
)
