package com.attend.attendance_api.service

import com.attend.attendance_api.dto.UserRequest
import com.attend.attendance_api.dto.UserResponse
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
}
