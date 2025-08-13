package com.attend.attendance_api.service

import com.attend.attendance_api.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository
) {
}