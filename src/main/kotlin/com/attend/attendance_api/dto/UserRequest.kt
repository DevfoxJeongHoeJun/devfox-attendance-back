package com.attend.attendance_api.DTO

import java.time.LocalDateTime

data class UserRequest (

    val id: Long,
    val accessLevelCode: String,
    val groupCode: String,
    val name: String,
    val email: String,
    val password: String,
    var createdAt: LocalDateTime,
    var createdUser: Long,
    var updatedAt: LocalDateTime,
    var updatedUser: Long
)