package com.attend.attendance_api.service

import com.attend.attendance_api.repository.GroupRepository
import com.attend.attendance_api.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class AppService(
    private val userRepository: UserRepository,
    private val groupRepository: GroupRepository
) {
}