package com.attend.attendance_api.dto

import java.time.LocalDate
import java.time.LocalDateTime

data class AttendStartRequest (

//    val id: Long,
    val userId: Long,
    val date: LocalDate,
    val type: String,
    val startTime: LocalDateTime,
    var startLocation: String,
//    val endTime: LocalDateTime,
//    var endLocation: String,
    var createdAt: LocalDateTime,
    var createdUser: Long,
//    var updatedAt: LocalDateTime,
//    var updatedUser: Long
)