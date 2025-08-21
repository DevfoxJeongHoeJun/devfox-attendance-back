package com.attend.attendance_api.service

import com.attend.attendance_api.dto.AttendEndRequest
import com.attend.attendance_api.dto.AttendResponse
import com.attend.attendance_api.dto.AttendStartRequest
import com.attend.attendance_api.dto.LoginRequest
import com.attend.attendance_api.dto.LoginResDto
import com.attend.attendance_api.dto.UserCreateRequest
import com.attend.attendance_api.entity.UserEntity
import com.attend.attendance_api.repository.UserRepository
import jakarta.servlet.http.HttpSession
import com.attend.attendance_api.dto.UserRequest
import com.attend.attendance_api.dto.UserResponse
import com.attend.attendance_api.entity.AttendEntity
import com.attend.attendance_api.repository.AttendanceRepository
import jakarta.persistence.EntityNotFoundException
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.crypto.password.PasswordEncoder
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime


@Service
class UserService(
    private val userRepository: UserRepository,
    private val attendanceRepository: AttendanceRepository,
    private val passwordEncoder: PasswordEncoder
) {

    fun login(session: HttpSession,loginRequest: LoginRequest,res: HttpServletResponse): LoginResDto? {

        val userEntity = userRepository.findByEmail(loginRequest.email)
            .orElse(null)

        if(userEntity != null){

            if (passwordEncoder.matches(loginRequest.password, userEntity.password)){

                session.setAttribute("username", userEntity.name)
                session.setAttribute("userId", userEntity.id)
                session.setAttribute("role", userEntity.accessLevelCode)
                session.setAttribute("groupCode", userEntity.groupCode)

                val cookieUserName = Cookie("username",  session.getAttribute("username")?.toString())
                cookieUserName.isHttpOnly = true
                cookieUserName.secure = false
                cookieUserName.maxAge = 60 * 60 * 12
                cookieUserName.path = "/"
                val cookieUserId = Cookie("userId", session.getAttribute("userId")?.toString())
                cookieUserId.isHttpOnly = true
                cookieUserId.secure = false
                cookieUserId.maxAge = 60 * 60 * 12
                cookieUserId.path = "/"
                val cookieRole = Cookie("role", session.getAttribute("role")?.toString())
                cookieRole.isHttpOnly = true
                cookieRole.secure = false
                cookieRole.maxAge = 60 * 60 * 12
                cookieRole.path = "/"
                val cookieGroupCode = Cookie("groupCode", session.getAttribute("groupCode")?.toString())
                cookieGroupCode.isHttpOnly = true
                cookieGroupCode.secure = false
                cookieGroupCode.maxAge = 60 * 60 * 12
                cookieGroupCode.path = "/"
                val cookieSessionId = Cookie("cookieSessionId", session.id)
                cookieSessionId.isHttpOnly = true
                cookieSessionId.secure = false
                cookieSessionId.maxAge = 60 * 60 * 12
                cookieSessionId.path = "/"


                res.addCookie(cookieSessionId)
                res.addCookie(cookieUserName)
                res.addCookie(cookieUserId)
                res.addCookie(cookieRole)
                res.addCookie(cookieGroupCode)

                return LoginResDto(userEntity);
            }

        } else {
            return null;
        }
        return null;
    }

    fun existsByEmail(email: String): Boolean {
        val existsByEmail = userRepository.existsByEmail(email);

        return existsByEmail;
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
    fun createUser(
        request: UserCreateRequest
    ): UserResponse {
        val encodedPassword: String = passwordEncoder.encode(request.password);

        val user = UserEntity(
            accessLevelCode = "ROLE_USER",
            groupCode = request.groupCode,
            name = request.name,
            email = request.email,
            password = encodedPassword,
        )
        val saveUser = userRepository.save(user);

        return UserResponse(
            id = user.id,
            name = user.name,
            email = user.email
        )
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
    fun startWork(request: AttendStartRequest): AttendEntity {

        val typeCode = when (request.type) {
            "出社" -> 1
            "在宅" -> 2
            else -> 0
        }
        val attend = AttendEntity(
            userId = request.userId,
            date = request.date,
            type = typeCode.toString(),
            startTime = request.startTime,
            startLocation = request.startLocation,
            endTime = null,
            endLocation = "",
            createdAt = LocalDateTime.now(),
            createdUser = request.createdUser,
            updatedAt = null,
            updatedUser = null
        )
        return attendanceRepository.save(attend)
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

    //勤怠詳細情報
    fun getMonthlyRecord(userId: Long, year: Int, month: Int): Map<String, Any> {
        val attendList = attendanceRepository.findByUserIdAndYearMonth(userId, year, month)

        val totalMinutes = attendList.sumOf { record ->
            val start = record.startTime
            val end = record.endTime ?: start
            java.time.Duration.between(start, end).toMinutes()
        }

        val hours = totalMinutes / 60

        return mapOf(
            "workDaysCount" to attendList.size, // 出勤数
            "totalHours" to hours // 総勤務時間(分)
        )
    }

}


