package com.attend.attendance_api.dto

data class GroupCreateRequest(

    val groupName: String,
    val address: String,
    val domain: String,
    val userName: String,
    val email: String,
    val password: String,

)