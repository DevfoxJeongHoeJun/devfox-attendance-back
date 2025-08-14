package com.attend.attendance_api.dto

import java.time.LocalDate

data class AttendListRequest(

    val groupCode: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val status: String,
    val userName: String

)