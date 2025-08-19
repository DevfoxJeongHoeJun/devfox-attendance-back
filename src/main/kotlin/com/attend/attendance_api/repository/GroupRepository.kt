package com.attend.attendance_api.repository

import com.attend.attendance_api.dto.GroupCreateResponse
import com.attend.attendance_api.dto.GroupProjection
import com.attend.attendance_api.entity.GroupEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface GroupRepository : JpaRepository<GroupEntity, Long> {

    // グループ登録する時、グループコード生成のために、DBからMAX（一番高い）コードを取得
    @Query(
        value = """
        SELECT COALESCE(
            MAX(CAST(SUBSTRING(code, 2, LENGTH(code)) AS INTEGER)),
            0) + 1 AS group_code
        FROM public.groups
    """, nativeQuery = true
    )
    fun getMaxGroupCode(): Int

    // グループコードからグループ詳細情報を取得
    fun findByCode(groupCode: String): GroupEntity

    @Query(
        """
        SELECT 
            g.code AS groupCode,
            g.name AS groupName,
            g.address AS address,
            g.domain AS domain,
            COUNT(u.id) AS memberCount
        FROM groups g
        LEFT JOIN users u ON g.id = u.group_id
        WHERE (:keyword IS NULL OR LOWER(g.name) LIKE LOWER(CONCAT('%', :keyword, '%')))
        GROUP BY g.id, g.code, g.name, g.address, g.domain
        """,
        nativeQuery = true
    )
    fun findGroupsWithMemberCount(@Param("keyword") keyword: String?): List<GroupProjection>
}

interface GroupMemberCountProjection {
    val id: Long
    val name: String
    val memberCount: Long
}