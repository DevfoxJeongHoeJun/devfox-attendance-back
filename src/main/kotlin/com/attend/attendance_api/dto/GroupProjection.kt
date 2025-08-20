package com.attend.attendance_api.dto

interface GroupProjection {
    val code: String
    val groupName: String
    val address: String
    val domain: String
    val memberCount: Long
}