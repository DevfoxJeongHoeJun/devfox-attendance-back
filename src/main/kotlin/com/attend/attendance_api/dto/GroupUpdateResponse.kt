package com.attend.attendance_api.dto

data class GroupUpdateResponse(
    val groupCode: Int,
    val groupName: String,
    val groupAddress: String,
    val groupDomain: String
)