package com.attend.attendance_api.service

import com.attend.attendance_api.dto.AttendListRequest
import com.attend.attendance_api.dto.AttendListResponse
import com.attend.attendance_api.dto.GroupCreateRequest
import com.attend.attendance_api.dto.GroupCreateResponse
import com.attend.attendance_api.dto.GroupInfoResponse
import com.attend.attendance_api.dto.GroupUpdateRequest
import com.attend.attendance_api.dto.GroupUpdateResponse
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

    // グループコードから該当グループの詳細情報を取得
    fun getGroupInfoByGroupCode(
        groupCode: String
    ): GroupInfoResponse {
        val group = groupRepository.findByCode(groupCode);

        return GroupInfoResponse(
            groupCode = group.code,
            groupName = group.name,
            groupAddress = group.address,
            groupDomain = group.domain
        )
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
            email = saveUser.email
        )
    }

    // グループ情報を編集
    @Transactional
    fun updateGroup(
        request: GroupUpdateRequest
    ): GroupUpdateResponse {

        var group = groupRepository.findByCode(request.groupCode)

        request.groupName.takeIf { it.isNotBlank() }?.let { group.name = it }
        request.groupAddress.takeIf { it.isNotBlank() }?.let { group.address = it }

        return GroupUpdateResponse(
            groupCode = group.code,
            groupName = group.name,
            groupAddress = group.address,
            groupDomain = group.domain
        )
    }
}