package com.attend.attendance_api.dto

import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.persistence.Column
import java.time.LocalDate
import java.time.LocalDateTime

data class AttendStartRequest (

//    val id: Long,
    val userId: Long,

    @field:JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    val date: LocalDate,

    val type: String,

    @field:JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    val startTime: LocalDateTime,

    var startLocation: String,

    var createdAt: LocalDateTime= LocalDateTime.now(),

    var createdUser: Long=userId,

)