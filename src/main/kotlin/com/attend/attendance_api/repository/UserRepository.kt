package com.attend.attendance_api.repository

import com.attend.attendance_api.dto.AttendListResponse
import com.attend.attendance_api.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.LocalDate
import java.util.Optional

interface UserRepository: JpaRepository<UserEntity, Long> {
    fun findByEmail(email: String): Optional<UserEntity>

    fun existsByEmail(email: String): Boolean
}