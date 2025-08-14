package com.attend.attendance_api.repository

import com.attend.attendance_api.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface UserRepository: JpaRepository<UserEntity, Long> {
    fun  findByEmail(email: String): UserEntity
}