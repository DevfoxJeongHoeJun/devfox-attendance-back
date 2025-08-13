package com.attend.attendance_api.service

import com.attend.attendance_api.DTO.UserRequest
import com.attend.attendance_api.entity.UserEntity
import com.attend.attendance_api.repository.UserRepository
import jakarta.persistence.EntityNotFoundException
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository){

    //FindAll UserList
    fun getUsers(): MutableList<UserEntity>{
        return userRepository.findAll()
    }

    //Find User
    fun findById(id: Long): UserEntity? {
        return userRepository.findById(id).orElseThrow{
               throw EntityNotFoundException("IDが $id を見けられません。")
        }
    }

    //Save User
    fun saveDummyUser(request: UserRequest){
        //ユーザーを先に作る
        val user = UserEntity(
            id = request.id,
            accessLevelCode = request.accessLevelCode,
            groupCode = request.groupCode,
            name = request.name,
            email = request.email,
            password = request.password,
            createdAt = request.createdAt,
            createdUser = request.createdUser,
            updatedAt = request.updatedAt,
            updatedUser = request.updatedUser
        )
        userRepository.save(user)//ユーザーを作成した後

        //ここでトランザクション、
        @Transactional
        fun updateUser(request: UserRequest) {
            val user = userRepository.findById(request.id)
                .orElseThrow { IllegalArgumentException("User not found") }//ユーザーが存在しない場合エーラーを発生します。

            user?.let { it.id = request.id }
            user?.let { it.accessLevelCode = request.accessLevelCode }
            user?.let { it.groupCode = request.groupCode }
            user?.let { it.name = request.name }
            user?.let { it.email = request.email }
            user?.let { it.password = request.password }
            user?.let { it.createdAt = request.createdAt }
            user?.let { it.createdUser = request.createdUser }
            user?.let { it.updatedAt = request.updatedAt }
            user?.let { it.updatedUser = request.updatedUser }
        }
    }




}


