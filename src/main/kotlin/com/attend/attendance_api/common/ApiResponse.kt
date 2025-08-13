package com.attend.attendance_api.common

import org.springframework.web.bind.annotation.ResponseStatus

class ApiResponse<T>(
    var status: ResponseStatus,
    var message: String,
    var body: T
)