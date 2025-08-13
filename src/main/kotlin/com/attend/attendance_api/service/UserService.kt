package com.attend.attendance_api.service

import com.attend.attendance_api.dto.LoginRequest
import com.attend.attendance_api.dto.LoginResDto
import com.attend.attendance_api.entity.UserEntity
import com.attend.attendance_api.repository.UserRepository
import jakarta.servlet.http.HttpSession
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(private val userRepository: UserRepository) {

    @Transactional(readOnly = true)
    fun login(session: HttpSession,loginRequest: LoginRequest): LoginResDto {
        var userEntiry : UserEntity?
        userEntiry = userRepository.findByEmail(loginRequest.email)


        if(userEntiry != null){

            if (userEntiry.password == loginRequest.password){

                if(userEntiry != null){
                    session.setAttribute("username", userEntiry.name)
                    session.setAttribute("userId", userEntiry.id)
                    session.setAttribute("ROLE", userEntiry.accessLevelCode)
                    session.setAttribute("groupCode", userEntiry.groupCode)

                    println(session.getAttribute("username"))
                    println(session.getAttribute("userId"))
                    println(session.getAttribute("ROLE"))
                    println(session.getAttribute("groupCode"))
                }
                var loginResDto = LoginResDto(userEntiry)
                return loginResDto;
            }

        }
            var loginResDto = LoginResDto(userEntiry)
        return loginResDto;
    }
}
