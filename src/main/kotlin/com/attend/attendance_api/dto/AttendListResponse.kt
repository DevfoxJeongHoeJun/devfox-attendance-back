package com.attend.attendance_api.dto

import java.time.LocalDate
import java.time.LocalDateTime

interface AttendListResponse{

    val userId: Long
    val userName: String
    val type: String?
    val date: LocalDate
    val startTime: LocalDateTime?
    val endTime: LocalDateTime?
    val startLocation: String?
    val endLocation: String?

}

