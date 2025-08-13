package com.attend.attendance_api.controller

import com.attend.attendance_api.common.ApiResponse
import com.attend.attendance_api.dto.LoginRequest
import com.attend.attendance_api.dto.LoginResDto
import com.attend.attendance_api.service.UserService
import jakarta.servlet.http.HttpServletResponse
import jakarta.servlet.http.HttpSession
import org.springframework.http.HttpStatus
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
    fun login(session: HttpSession, res: HttpServletResponse, @RequestBody loginRequest: LoginRequest): ApiResponse<LoginResDto> {
        val loginResDto: LoginResDto = userService.login(session, loginRequest)

        if (loginResDto != null) {
            return ApiResponse(HttpStatus.OK, "成功",loginResDto)
        }
        return ApiResponse(HttpStatus.NOT_FOUND, "失敗",loginResDto)
    }

}