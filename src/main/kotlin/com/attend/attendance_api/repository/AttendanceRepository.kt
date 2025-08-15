package com.attend.attendance_api.repository

import com.attend.attendance_api.dto.AttendListResponse
import com.attend.attendance_api.entity.AttendEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.LocalDate

interface AttendanceRepository: JpaRepository<AttendEntity, Long> {

    @Query("""
        WITH dates AS (
            SELECT DISTINCT date
            FROM attendance
                WHERE date BETWEEN :startDate AND :endDate
        )
        
        SELECT
            d.date,
            u.id AS user_id,
            u.name AS user_name,
            a.start_time,
            a.end_time,
            a.start_location,
            a.end_location
        FROM users u
        CROSS JOIN dates d
        LEFT JOIN attendance a
            ON a.user_id = u.id
            AND a.date = d.date
        WHERE u.group_code = :groupCode
        AND (u.access_level_code = 'ROLE_USER' OR u.access_level_code = 'ROLE_MANAGER')
        AND u.name LIKE CONCAT('%', :userName, '%')
        AND (
            :status = '0'
            OR(:status = '1' AND a.user_id IS NULL) 
        )
        ORDER BY d.date, user_id;
    """,nativeQuery = true)
    fun getAttendList(
        @Param("groupCode") groupCode: String,
        @Param("status") status: String,
        @Param("startDate") startDate: LocalDate,
        @Param("endDate") endDate: LocalDate,
        @Param("userName") userName: String
    ): List<AttendListResponse>

    fun findByUserIdAndDate(userId: Long, date: LocalDate): AttendEntity? //idを利用してユーザーを探す
}