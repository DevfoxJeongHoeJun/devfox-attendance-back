package com.attend.attendance_api.service

import com.attend.attendance_api.dto.GroupCreateResponse
import com.attend.attendance_api.dto.UserRequest
import com.attend.attendance_api.dto.UserResponse
import com.attend.attendance_api.dto.UsersListResponse
import com.attend.attendance_api.repository.GroupRepository
import com.attend.attendance_api.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class AppService(
    private val userRepository: UserRepository,
    private val groupRepository: GroupRepository
) {
    fun getUserById(id: Long): UserResponse {
        val user = userRepository.findById(id).orElseThrow()
        return UserResponse(
            id = user.id!!,
            name = user.name,
            email = user.email
        )
    }

    fun updateUser(request: UserRequest) {
        val user = userRepository.findById(request.id).orElseThrow()
        user.name = request.name
        user.email = request.email
        user.password = request.password
        userRepository.save(user)
    }

    fun deleteUser(id: Long) {
        userRepository.deleteById(id)
    }

//    fun getUsersWithGroup(pageable: Pageable): Page<UsersListResponse> {
//        return userRepository.getUsersWithGroup(pageable)
//    }
//
//    fun searchUsersWithGroup(pageable: Pageable, username: String): Page<UsersListResponse> {
//        return userRepository.getUsersWithGroupSearch(username, pageable)
//    }

    fun getUsersWithGroup(): List<UsersListResponse> {
        return userRepository.getUsersWithGroup()
    }

    fun searchUsersWithGroup(username: String): List<UsersListResponse> {
        return userRepository.getUsersWithGroupSearch(username)
    }

    fun getGroups(keyword: String?): List<GroupCreateResponse> {
        val projections = groupRepository.findGroupsWithMemberCount(keyword)
        return projections.map { p ->
            GroupCreateResponse(
                groupCode = p.code,
                groupName = p.name,
                address = p.address,
                domain = p.domain,
                memberCount = p.memberCount.toInt(),
                userName = "",
                email = ""
            )
        }
    }
    fun getAllGroups(): List<GroupCreateResponse> {
        return groupRepository.findAll().map { GroupCreateResponse.of(it) }
    }


    fun searchGroupsByName(keyword: String): List<GroupCreateResponse> {
        return groupRepository.findByNameContainingIgnoreCase(keyword)
            .map { GroupCreateResponse.of(it) }
    }
}
