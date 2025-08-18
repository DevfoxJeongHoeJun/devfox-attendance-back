package com.attend.attendance_api.controller

import com.attend.attendance_api.common.ApiResponse
import com.attend.attendance_api.dto.AttendListRequest
import com.attend.attendance_api.dto.AttendListResponse
import com.attend.attendance_api.dto.UsersListResponse
import com.attend.attendance_api.service.AppService
import com.attend.attendance_api.service.GroupService
import com.attend.attendance_api.service.UserService
import jakarta.servlet.http.HttpSession
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/app")
class AppController(
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

}