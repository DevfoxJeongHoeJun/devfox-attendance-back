package com.attend.attendance_api.repository

import com.attend.attendance_api.entity.AccessLevelEntity
import org.springframework.data.jpa.repository.JpaRepository

interface AccessLevelRepository: JpaRepository<AccessLevelEntity, Long> {
}