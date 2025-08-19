package com.attend.attendance_api.repository

import com.attend.attendance_api.dto.AttendListResponse
import com.attend.attendance_api.dto.UsersListResponse
import com.attend.attendance_api.entity.UserEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.LocalDate
import java.util.Optional

interface UserRepository: JpaRepository<UserEntity, Long> {
    fun findByEmail(email: String): Optional<UserEntity>

//    @Query(
//        value = """
//        SELECT
//            u.id AS user_id,
//            u.name AS user_name,
//            g.name AS group_name,
//            u.access_level_code
//        FROM users u
//        LEFT JOIN groups g
//            ON u.group_code = g.code
//        WHERE (u.access_level_code = 'ROLE_USER' OR u.access_level_code = 'ROLE_MANAGER')
//          AND u.name LIKE %:username%
//        ORDER BY u.id
//    """,
////        countQuery = """
////        SELECT COUNT(*)
////        FROM users u
////        LEFT JOIN groups g
////            ON u.group_code = g.code
////        WHERE (u.access_level_code = 'ROLE_USER' OR u.access_level_code = 'ROLE_MANAGER')
////          AND u.name LIKE %:username%
////    """,
//        nativeQuery = true
//    )
//    fun getUsersWithGroupSearch(@Param("username") username: String, pageable: Pageable): Page<UsersListResponse>
//
//    @Query(
//        value = """
//    SELECT
//        u.id AS user_id,
//        u.name AS user_name,
//        g.name AS group_name,
//        u.access_level_code
//    FROM users u
//    LEFT JOIN groups g
//        ON u.group_code = g.code
//    WHERE (u.access_level_code = 'ROLE_USER' OR u.access_level_code = 'ROLE_MANAGER')
//    ORDER BY u.id
//""",
////        countQuery = """
////    SELECT COUNT(*)
////    FROM users u
////    LEFT JOIN groups g
////        ON u.group_code = g.code
////    WHERE (u.access_level_code = 'ROLE_USER' OR u.access_level_code = 'ROLE_MANAGER')
////""",
//        nativeQuery = true
//    )
//    fun getUsersWithGroup(pageable: Pageable): Page<UsersListResponse>

    @Query(
        value = """
        SELECT 
            u.id AS user_id,
            u.name AS user_name,
            g.name AS group_name,
            u.access_level_code
        FROM users u
        LEFT JOIN groups g
            ON u.group_code = g.code
        WHERE (u.access_level_code = 'ROLE_USER' OR u.access_level_code = 'ROLE_MANAGER')
          AND u.name LIKE %:username%
        ORDER BY u.id
    """,
//        countQuery = """
//        SELECT COUNT(*)
//        FROM users u
//        LEFT JOIN groups g
//            ON u.group_code = g.code
//        WHERE (u.access_level_code = 'ROLE_USER' OR u.access_level_code = 'ROLE_MANAGER')
//          AND u.name LIKE %:username%
//    """,
        nativeQuery = true
    )
    fun getUsersWithGroupSearch(@Param("username") username: String): List<UsersListResponse>

    @Query(
        value = """
    SELECT 
        u.id AS user_id,
        u.name AS user_name,
        g.name AS group_name,
        u.access_level_code
    FROM users u
    LEFT JOIN groups g
        ON u.group_code = g.code
    WHERE (u.access_level_code = 'ROLE_USER' OR u.access_level_code = 'ROLE_MANAGER')
    ORDER BY u.id
""",
//        countQuery = """
//    SELECT COUNT(*)
//    FROM users u
//    LEFT JOIN groups g
//        ON u.group_code = g.code
//    WHERE (u.access_level_code = 'ROLE_USER' OR u.access_level_code = 'ROLE_MANAGER')
//""",
        nativeQuery = true
    )

    fun getUsersWithGroup(): List<UsersListResponse>

    fun existsByEmail(email: String): Boolean

}