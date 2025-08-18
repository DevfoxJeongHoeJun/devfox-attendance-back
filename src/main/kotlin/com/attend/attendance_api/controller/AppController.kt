package com.attend.attendance_api.controller

import com.attend.attendance_api.dto.UserRequest
import com.attend.attendance_api.service.AppService
import com.attend.attendance_api.service.GroupService
import org.springframework.web.bind.annotation.*
import org.springframework.http.ResponseEntity
import com.attend.attendance_api.dto.UserResponse

@CrossOrigin(origins = ["http://localhost:60259"])
@RestController
@RequestMapping("/api/app")
class AppController(
    private val groupService: GroupService,
    private val appService: AppService
) {

    @GetMapping("/{id}")
    fun getUser(@PathVariable id: Long): ResponseEntity<UserResponse> {
        val user = appService.getUserById(id)
        return ResponseEntity.ok(user)
    }

    @PostMapping("/update")
    fun updateUser(@RequestBody request: UserRequest): ResponseEntity<String> {
        appService.updateUser(request)
        return ResponseEntity.ok("更新完了")
    }

    @DeleteMapping("/delete/{id}")
    fun deleteUser(@PathVariable id: Long): ResponseEntity<String> {
        appService.deleteUser(id)
        return ResponseEntity.ok("削除完了")
    }

}