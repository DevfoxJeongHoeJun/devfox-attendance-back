package com.attend.attendance_api.dto

interface UsersListResponse {
    val userId: Long
    val userName: String
    val groupName: String?
    val accessLevelCode: String
}