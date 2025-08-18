package com.attend.attendance_api.controller

import com.attend.attendance_api.dto.UserRequest
import com.attend.attendance_api.dto.UserResponse
import com.attend.attendance_api.dto.UsersListResponse
import com.attend.attendance_api.service.AppService
import com.attend.attendance_api.service.GroupService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = ["http://localhost:*"])
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

    @GetMapping("/usersList")
    fun getUsers(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): Page<UsersListResponse> {
        val pageable: Pageable = PageRequest.of(page, size)
        return appService.getUsersWithGroup(pageable)
    }
}
