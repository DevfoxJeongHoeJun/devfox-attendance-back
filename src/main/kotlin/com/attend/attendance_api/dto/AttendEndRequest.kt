package com.attend.attendance_api.dto

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

data class AttendEndRequest(
    @field:JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    val endTime: LocalDateTime,

    val endLocation: String,

    val updatedUser: Long
)
