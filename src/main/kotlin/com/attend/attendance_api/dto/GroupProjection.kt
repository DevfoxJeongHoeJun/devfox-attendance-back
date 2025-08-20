package com.attend.attendance_api.dto

interface GroupProjection {
    val code: String
    val name: String
    val address: String
    val domain: String
    val memberCount: Long
}