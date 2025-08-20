package com.attend.attendance_api.dto

import com.attend.attendance_api.entity.GroupEntity

class GroupCreateResponse(

    val groupCode: String,
    val groupName: String,
    val address: String,
    val domain: String,
    val userName: String,
    val email: String,
    val memberCount: Int

)