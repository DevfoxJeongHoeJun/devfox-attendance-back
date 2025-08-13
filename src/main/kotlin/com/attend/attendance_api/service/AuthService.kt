package com.attend.attendance_api.service

import com.attend.attendance_api.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val userRepository: UserRepository
) {

}