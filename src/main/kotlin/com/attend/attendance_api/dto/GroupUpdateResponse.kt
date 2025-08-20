package com.attend.attendance_api.dto

data class GroupUpdateResponse(
    val groupCode: String,
    val groupName: String,
    val groupAddress: String,
    val groupDomain: String
)