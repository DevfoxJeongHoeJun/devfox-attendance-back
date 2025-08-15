package com.attend.attendance_api.controller

import com.attend.attendance_api.common.ApiResponse
import com.attend.attendance_api.dto.LoginRequest
import com.attend.attendance_api.dto.LoginResDto
import com.attend.attendance_api.service.UserService
import jakarta.servlet.http.HttpServletResponse
import jakarta.servlet.http.HttpSession
import org.springframework.http.HttpStatus
import com.attend.attendance_api.dto.UserRequest
import com.attend.attendance_api.entity.UserEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@CrossOrigin(origins = ["http://localhost:8080", "http://localhost:30000"])
@RestController
@RequestMapping("/api/user")
class UserController(private val userService: UserService) {

    @PostMapping("/login")
    fun login(session: HttpSession, res: HttpServletResponse, @RequestBody loginRequest: LoginRequest): ApiResponse<LoginResDto> {
        val loginResDto: LoginResDto = userService.login(session, loginRequest, res)

        if (loginResDto != null) {
            return ApiResponse(HttpStatus.OK, "成功",loginResDto)
        }
        return ApiResponse(HttpStatus.NOT_FOUND, "失敗",loginResDto)
    }

    @GetMapping("/session")
    fun session(session: HttpSession,res: HttpServletResponse): ApiResponse<String> {
        println("session")
        println(session.getAttribute("userId"))
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

    //勤怠打刻画面-------------------------------------------
//    @GetMapping("attendInfo")
//    fun attendInfo(id: Long): AttendEntity{
//
//    }


    //位置情報確認
//    @PostMapping("/location")
//    fun receiveLocation(@RequestBody location: LocationRequest): String{
//        println("受け取った位置: 緯度=${location.latitude}, 経度=${location.longitude}")
//        return "位置受信成功"
//    }
}