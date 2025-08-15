package com.attend.attendance_api.controller

import com.attend.attendance_api.common.ApiResponse
import com.attend.attendance_api.dto.AttendListRequest
import com.attend.attendance_api.dto.AttendListResponse
import com.attend.attendance_api.service.GroupService
import com.attend.attendance_api.service.UserService
import jakarta.servlet.http.HttpSession
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/group")
class GroupController(
    private val groupService: GroupService,
    private val userService: UserService,
) {

    @PostMapping("/attend-list")
    fun getAttendList(
        @RequestBody request: AttendListRequest,
        session: HttpSession
    ): ApiResponse<List<AttendListResponse>>{
        // Session ユーザーチェック
        if (session.getAttribute("userId") == null) {
            return ApiResponse(HttpStatus.UNAUTHORIZED, "失敗",null);
        }

        val response:List<AttendListResponse> = groupService.getAttendList(request)

        return ApiResponse(HttpStatus.OK, "成功", response)
    }
}