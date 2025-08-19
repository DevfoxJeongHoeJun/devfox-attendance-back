package com.attend.attendance_api.dto

data class GroupUpdateRequest(
    val groupCode: String,
    val groupName: String,
    val groupAddress: String
)