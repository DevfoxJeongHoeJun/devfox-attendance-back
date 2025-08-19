package com.attend.attendance_api.controller

import com.attend.attendance_api.common.ApiResponse
import com.attend.attendance_api.dto.AttendListRequest
import com.attend.attendance_api.dto.AttendListResponse
import com.attend.attendance_api.dto.GroupCreateRequest
import com.attend.attendance_api.dto.GroupCreateResponse
import com.attend.attendance_api.dto.GroupInfoResponse
import com.attend.attendance_api.dto.GroupUpdateRequest
import com.attend.attendance_api.dto.GroupUpdateResponse
import com.attend.attendance_api.service.GroupService
import com.attend.attendance_api.service.UserService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController
@RequestMapping("/api/group")
class GroupController(
    private val groupService: GroupService,
    private val userService: UserService,
) {

    // 勤怠一覧リスト取得API
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

    // 新規登録（グループとグループ管理者）
    @PostMapping("/create")
    fun createGroupAndAdmin(
        @RequestBody request: GroupCreateRequest
    ): ApiResponse<GroupCreateResponse>{
        val response: GroupCreateResponse = groupService.createGroupAndAdmin(request);

        return ApiResponse(HttpStatus.OK, "成功", response)
    }

    // グループ情報を編集
    @PostMapping("/update")
    fun updateGroup(
        @RequestBody request: GroupUpdateRequest
    ): ApiResponse<GroupUpdateResponse>{
        val response: GroupUpdateResponse = groupService.updateGroup(request);

        return ApiResponse(HttpStatus.OK, "成功", response)
    }

    // 勤怠詳細画面ー初期値取得API
    @GetMapping("/info/{groupCode}")
    fun getGroupInfoByGroupCode(
        @PathVariable groupCode: String
    ): ApiResponse<GroupInfoResponse> {
        val response: GroupInfoResponse = groupService.getGroupInfoByGroupCode(groupCode);

        return ApiResponse(HttpStatus.OK, "成功", response)
    }
}