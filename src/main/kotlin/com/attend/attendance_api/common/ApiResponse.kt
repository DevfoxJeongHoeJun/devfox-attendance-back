package com.attend.attendance_api.common

import org.springframework.http.HttpStatus

class ApiResponse<T>(
    var status: HttpStatus,
    var message: String,
    var body: T
)