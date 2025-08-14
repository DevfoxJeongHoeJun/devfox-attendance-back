package com.attend.attendance_api.dto

import lombok.Getter

//@Getter
data class LoginRequest(
    val email: String,
    val password: String
)