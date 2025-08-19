package com.attend.attendance_api.controller

import com.attend.attendance_api.dto.MailRequest
import com.attend.attendance_api.service.MailService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/mail")
class MailController(
    private val mailService: MailService
) {

    @PostMapping("/invite")
    fun sendInviteMail(
        @RequestBody mailRequest: MailRequest
    ) {
        mailService.sendInviteMail(mailRequest);
    }
}