package com.attend.attendance_api.controller

import com.attend.attendance_api.service.UserService
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@CrossOrigin(origins = ["http://localhost:8080", "http://localhost:30000"])
@RestController
@RequestMapping("/api/user")
class UserController(
    private val userService: UserService) {

    @PostMapping("/login")
    fun login(@RequestBody user: Map<String, String>): Map<String, String> {
        val username = user["username"]
        val password = user["password"]

        println("受信: username=$username, password=$password")

        return mapOf(
            "status" to if (username == "testuser" && password == "1234") "ok" else "error",
            "message" to "サーバーからの応答です"
        )
    }

}