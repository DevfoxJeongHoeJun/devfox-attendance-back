package com.attend.attendance_api.dto

data class MailRequest(

    val toMail: String,
    val inviteUrl: String,
    val groupName: String
)