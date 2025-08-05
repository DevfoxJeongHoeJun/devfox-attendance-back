package com.attend.attendance_api.repository

import com.attend.attendance_api.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<UserEntity, Long> {
}