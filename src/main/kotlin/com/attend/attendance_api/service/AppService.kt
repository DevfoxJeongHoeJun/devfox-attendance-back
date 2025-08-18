package com.attend.attendance_api.service

import com.attend.attendance_api.dto.AttendListResponse
import com.attend.attendance_api.dto.UsersListResponse
import com.attend.attendance_api.repository.GroupRepository
import com.attend.attendance_api.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

@Service
class AppService(
    private val userRepository: UserRepository,
    private val groupRepository: GroupRepository
) {

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
}