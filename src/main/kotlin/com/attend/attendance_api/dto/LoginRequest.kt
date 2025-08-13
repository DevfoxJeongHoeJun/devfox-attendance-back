package com.attend.attendance_api.dto

import lombok.Getter

@Getter
class LoginRequest(
    val email: String,
    val password: String
)