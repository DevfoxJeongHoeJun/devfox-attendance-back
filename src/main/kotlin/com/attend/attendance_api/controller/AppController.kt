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

//    @GetMapping("/usersList")
//    fun getUsers(
//        @RequestParam(defaultValue = "0") page: Int,
//        @RequestParam(defaultValue = "10") size: Int): Page<UsersListResponse> {
//        val pageable: Pageable = PageRequest.of(page, size)
//        val response: Page<UsersListResponse> = appService.getUsersWithGroup(pageable)
//
//        return response
//    }
//    @GetMapping("/searchUsersList")
//    fun getSearchUsers(
//        @RequestParam(defaultValue = "0") page: Int,
//        @RequestParam(defaultValue = "10") size: Int,
//        @RequestParam username: String
//    ): Page<UsersListResponse> {
//        val pageable: Pageable = PageRequest.of(page, size)
//        println(appService.searchUsersWithGroup(pageable, username))
//        return appService.searchUsersWithGroup(pageable, username)
//    }
    @GetMapping("/usersList")
    fun getUsers(): List<UsersListResponse> {
        val response: List<UsersListResponse> = appService.getUsersWithGroup()
        return response
    }

    @GetMapping("/searchUsersList")
    fun getSearchUsers(
        @RequestParam username: String): List<UsersListResponse> {
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

}

