package com.attend.attendance_api.controller

import com.attend.attendance_api.service.GroupService
import com.attend.attendance_api.service.UserService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/app")
class AppController(
    private val groupService: GroupService,
    private val userService: UserService
) {

//asdasdasdsa




}