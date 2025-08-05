package com.attend.attendance_api.repository

import com.attend.attendance_api.entity.GroupEntity
import org.springframework.data.jpa.repository.JpaRepository

interface GroupRepository: JpaRepository<GroupEntity, Long> {
}