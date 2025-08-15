package com.attend.attendance_api.service

import com.attend.attendance_api.dto.AttendListRequest
import com.attend.attendance_api.dto.AttendListResponse
import com.attend.attendance_api.repository.AttendanceRepository
import com.attend.attendance_api.repository.GroupRepository
import com.attend.attendance_api.repository.UserRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class GroupService(
    private val userRepository: UserRepository,
    private val attendanceRepository: AttendanceRepository,
    private val groupRepository: GroupRepository
) {

    // 該当グループのユーザー勤怠一覧情報を検索条件によって取得
    fun getAttendList(
        request: AttendListRequest,
        pageable: Pageable
        ): Page<AttendListResponse> {

        val attendList: Page<AttendListResponse> =
            attendanceRepository.getAttendList(
                request.groupCode,
                request.status,
                request.startDate,
                request.endDate,
                request.userName,
                pageable
            )

        return attendList
    }
}