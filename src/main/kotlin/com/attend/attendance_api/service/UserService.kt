package com.attend.attendance_api.service

import com.attend.attendance_api.dto.AttendEndRequest
import com.attend.attendance_api.dto.AttendResponse
import com.attend.attendance_api.dto.AttendStartRequest
import com.attend.attendance_api.dto.LoginRequest
import com.attend.attendance_api.dto.LoginResDto
import com.attend.attendance_api.entity.UserEntity
import com.attend.attendance_api.repository.UserRepository
import jakarta.servlet.http.HttpSession
import com.attend.attendance_api.dto.UserRequest
import com.attend.attendance_api.entity.AttendEntity
import com.attend.attendance_api.repository.AttendanceRepository
import jakarta.persistence.EntityNotFoundException
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletResponse
import java.time.LocalDate
import java.time.LocalDateTime


@Service
class UserService(private val userRepository: UserRepository, private val attendanceRepository: AttendanceRepository) {

    fun login(session: HttpSession,loginRequest: LoginRequest,res: HttpServletResponse): LoginResDto? {

        val userEntity = userRepository.findByEmail(loginRequest.email)
            .orElse(null)


        if(userEntity != null){

            if (userEntity.password == loginRequest.password){

                if(userEntity != null){
                    session.setAttribute("username", userEntity.name)
                    session.setAttribute("userId", userEntity.id)
                    session.setAttribute("role", userEntity.accessLevelCode)
                    session.setAttribute("groupCode", userEntity.groupCode)

                    val cookieUserName = Cookie("username",  session.getAttribute("username")?.toString())
                    cookieUserName.isHttpOnly = true
                    cookieUserName.secure = false
                    cookieUserName.maxAge = 60 * 60 * 12;
                    val cookieUserId = Cookie("userId", session.getAttribute("userId")?.toString())
                    cookieUserId.isHttpOnly = true
                    cookieUserId.secure = false
                    cookieUserId.maxAge = 60 * 60 * 12;
                    val cookieRole = Cookie("role", session.getAttribute("role")?.toString())
                    cookieRole.isHttpOnly = true
                    cookieRole.secure = false
                    cookieRole.maxAge = 60 * 60 * 12;
                    val cookieGroupCode = Cookie("groupCode", session.getAttribute("groupCode")?.toString())
                    cookieGroupCode.isHttpOnly = true
                    cookieGroupCode.secure = false
                    cookieGroupCode.maxAge = 60 * 60 * 12;

                    res.addCookie(cookieUserName)
                    res.addCookie(cookieUserId)
                    res.addCookie(cookieRole)
                    res.addCookie(cookieGroupCode)

                }
                var loginResDto = LoginResDto(userEntity)
                return loginResDto;
            }

        } else {
            return null;
        }
        var loginResDto = LoginResDto(userEntity)
        return loginResDto;
    }

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

    //Attendからユーザーを探す
    fun getAttend(userId: Long): AttendResponse? {
        val today = LocalDate.now()
        val attend = attendanceRepository.findByUserIdAndDate(userId, today)
        return attend?.let {
                AttendResponse(
                    id = it.id,
                    userId = it.userId,
                    date = it.date,
                    type = it.type,
                    startTime = it.startTime,
                    endTime = it.endTime
                )
            }
        }
    //出勤処理
    fun startWork(request: AttendStartRequest) {
        val attend = AttendEntity(
            id = 0, // auto increment
            userId = request.userId,
            date = request.date,
            type = request.type,
            startTime = request.startTime,
            startLocation = request.startLocation,
            endTime = null,
            endLocation = "",
            createdAt = LocalDateTime.now(),
            createdUser = request.createdUser,
            updatedAt = null,
            updatedUser = null
        )
        attendanceRepository.save(attend)
    }
    //退勤処理
    fun endWork(attendId: Long, request: AttendEndRequest) {

        val attend = attendanceRepository.findById(attendId)
            .orElseThrow { RuntimeException("出勤記録がありません。") }

            attend.endTime = request.endTime
            attend.endLocation = request.endLocation
            attend.updatedAt = LocalDateTime.now()
            attend.updatedUser = request.updatedUser

        attendanceRepository.save(attend)
    }
}


