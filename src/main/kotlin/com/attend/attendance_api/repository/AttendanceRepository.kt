package com.attend.attendance_api.repository

import com.attend.attendance_api.entity.AttendEntity
import org.springframework.data.jpa.repository.JpaRepository

interface AttendanceRepository: JpaRepository<AttendEntity, Long> {
}