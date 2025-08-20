package com.attend.attendance_api.controller

import com.attend.attendance_api.dto.GroupCreateResponse
import com.attend.attendance_api.dto.UserRequest
import com.attend.attendance_api.dto.UserResponse
import com.attend.attendance_api.dto.UsersListResponse
import com.attend.attendance_api.service.AppService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = ["*"])
@RestController
@RequestMapping("/api/app")
class AppController(
    private val appService: AppService
) {

    @GetMapping("/usersList")
    fun getUsers(): List<UsersListResponse> {
        return appService.getUsersWithGroup()
    }

    @GetMapping("/searchUsersList")
    fun getSearchUsers(@RequestParam username: String): List<UsersListResponse> {
        return appService.searchUsersWithGroup(username)
    }

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

    @GetMapping("/groups")
    fun getGroups(@RequestParam(required = false) name: String?): List<GroupCreateResponse> {
        return if (!name.isNullOrBlank()) {
            appService.searchGroupsByName(name)
        } else {
            appService.getAllGroups()
        }
    }

}
