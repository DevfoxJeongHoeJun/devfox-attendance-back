package com.attend.attendance_api.controller

import com.attend.attendance_api.DTO.LocationRequest
import com.attend.attendance_api.DTO.UserRequest
import com.attend.attendance_api.entity.AttendEntity
import com.attend.attendance_api.entity.UserEntity
import com.attend.attendance_api.service.UserService
import jakarta.transaction.Transactional
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
    fun login(@RequestBody user: Map<String, String>): Map<String, String> {
        val username = user["username"]
        val password = user["password"]

        println("受信: username=$username, password=$password")

        return mapOf(
            "status" to if (username == "testuser" && password == "1234") "ok" else "error",
            "message" to "サーバーからの応答です"
        )
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