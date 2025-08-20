package com.attend.attendance_api.dto

data class UserCreateRequest(
    val groupCode: String,
    val name: String,
    val email: String,
    val password: String,
)