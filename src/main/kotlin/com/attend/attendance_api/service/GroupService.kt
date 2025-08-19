package com.attend.attendance_api.service

import com.attend.attendance_api.dto.AttendListRequest
import com.attend.attendance_api.dto.AttendListResponse
import com.attend.attendance_api.dto.GroupCreateRequest
import com.attend.attendance_api.dto.GroupCreateResponse
import com.attend.attendance_api.entity.GroupEntity
import com.attend.attendance_api.entity.UserEntity
import com.attend.attendance_api.repository.AttendanceRepository
import com.attend.attendance_api.repository.GroupRepository
import com.attend.attendance_api.repository.UserRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GroupService(
    private val userRepository: UserRepository,
    private val attendanceRepository: AttendanceRepository,
    private val groupRepository: GroupRepository,
    private val passwordEncoder: PasswordEncoder
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

    // 新規登録（グループとグループ管理者）
    @Transactional
    fun createGroupAndAdmin(
        request: GroupCreateRequest,
    ): GroupCreateResponse {

        val maxCodeNumber: Int = groupRepository.getMaxGroupCode();
        val newCode: String = String.format("G%03d", maxCodeNumber);
        val encodedPassword: String = passwordEncoder.encode(request.password);

        val user = UserEntity(
            accessLevelCode = "ROLE_ADMIN",
            groupCode = newCode,
            name = request.userName,
            email = request.email,
            password = encodedPassword,
        )
        val saveUser = userRepository.save(user);

        val group = GroupEntity(
            code = newCode,
            name = request.groupName,
            address = request.address,
            domain = request.domain,
            createdUser = saveUser.id
        )
        val saveGroup = groupRepository.save(group);

        return GroupCreateResponse(
            groupCode = saveGroup.code,
            groupName = saveGroup.name,
            address = saveGroup.address,
            domain = saveGroup.domain,
            userName = saveUser.name,
            email = saveUser.email,
            memberCount = 0
        )
    }
}