package com.attend.attendance_api.controller

import com.attend.attendance_api.common.ApiResponse
import com.attend.attendance_api.dto.AttendListRequest
import com.attend.attendance_api.dto.AttendListResponse
import com.attend.attendance_api.service.GroupService
import com.attend.attendance_api.service.UserService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@CrossOrigin(origins = ["http://localhost:8080", "http://localhost:30000"])
@RestController
@RequestMapping("/api/group")
class GroupController(
    private val groupService: GroupService,
    private val userService: UserService,
) {

    @PostMapping("/attend-list")
    fun getAttendList(
        @RequestBody request: AttendListRequest,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): ApiResponse<Page<AttendListResponse>>{
        val pageable: Pageable = PageRequest.of(page, size);
        val response: Page<AttendListResponse> = groupService.getAttendList(request, pageable)

        return ApiResponse(HttpStatus.OK, "成功", response)
    }
}