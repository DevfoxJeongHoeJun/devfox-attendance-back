package com.attend.attendance_api.controller

import jakarta.servlet.http.HttpServletResponse
import jakarta.servlet.http.HttpSession
import org.springframework.http.HttpStatus
import com.attend.attendance_api.common.ApiResponse
import com.attend.attendance_api.dto.AttendEndRequest
import com.attend.attendance_api.dto.AttendStartRequest
import com.attend.attendance_api.dto.AttendResponse
import com.attend.attendance_api.dto.LoginRequest
import com.attend.attendance_api.dto.LoginResDto
import com.attend.attendance_api.service.UserService
import com.attend.attendance_api.dto.UserRequest
import com.attend.attendance_api.entity.UserEntity
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/user")
class UserController(private val userService: UserService) {

    @PostMapping("/login")
    fun login(session: HttpSession, res: HttpServletResponse,
              @RequestBody loginRequest: LoginRequest): ApiResponse<LoginResDto> {
        val loginResDto: LoginResDto? = userService.login(session, loginRequest, res)

        if (loginResDto != null) {
            return ApiResponse(HttpStatus.OK, "成功",loginResDto)
        }
        return ApiResponse(HttpStatus.NOT_FOUND, "失敗",loginResDto)
    }

    @GetMapping("/session")
    fun session(session: HttpSession,res: HttpServletResponse): ApiResponse<String> {
        println("session")

        if(session.getAttribute("userId") != null) {
            return ApiResponse(HttpStatus.OK, "成功", "sessionCheck")
        }
        return ApiResponse(HttpStatus.NOT_FOUND, "失敗","sessionCheck")
    }

    //Global Usage
    //全てのユーザーデータ入手
    @GetMapping("/getUsers")
    fun list(): MutableList<UserEntity> {
        return userService.getUsers()
    }

    //入力したユーザーでーたをデータベースに保存する
    @PostMapping("/addDummyUser")
    fun add(@RequestBody request: UserRequest): String {
        userService.saveDummyUser(request)
        return "user Add Success!"
    }

    //勤怠打刻画面------------------------------------------
    @GetMapping("/searchAttend/{userId}")
    fun getAttend(@PathVariable userId: Long): ResponseEntity<AttendResponse?> {
        val result = userService.getAttend(userId)
        return ResponseEntity.ok(result)
    }

    @PostMapping("/addAttend") //出勤処理
    fun addAttendRecord(@RequestBody request: AttendStartRequest): ResponseEntity<Map<String,Long>>{
        val saved = userService.startWork(request)
        return ResponseEntity.ok(mapOf("id" to saved.id)) //出勤処理完了
    }

    @PutMapping("/update/{attendId}")//退勤処理
    fun updateAttendRecord(@PathVariable attendId: Long,
                           @RequestBody request: AttendEndRequest): ResponseEntity<String?> {
        userService.endWork(attendId, request)
        return ResponseEntity.ok("退勤処理完了")
    }
    
    //アプリ管理者画面------------------------------------------
     @PostMapping("/appUserList")
    fun login(session: HttpSession, @RequestBody role: String): ApiResponse<String> {
        return ApiResponse(HttpStatus.OK, "success","sessionCheck")
    }
    @GetMapping("/existsByEmail/{email}")
    fun existsByEmail(@PathVariable email: String): ApiResponse<Boolean> {
        val existsByEmail = userService.existsByEmail(email);
        return ApiResponse(HttpStatus.OK, "成功", existsByEmail)
    }
}