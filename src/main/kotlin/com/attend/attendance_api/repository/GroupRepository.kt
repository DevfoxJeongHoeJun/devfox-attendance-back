package com.attend.attendance_api.repository

import com.attend.attendance_api.dto.GroupProjection
import com.attend.attendance_api.entity.GroupEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface GroupRepository : JpaRepository<GroupEntity, Long> {

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
        g.code AS code,
        g.name AS groupName,
        g.address AS address,
        g.domain AS domain,
        COUNT(u.id) AS memberCount
        FROM groups g
        LEFT JOIN users u ON g.code = u.group_code
        WHERE (:name IS NULL OR LOWER(g.name) LIKE LOWER(CONCAT('%', :name, '%')))
        GROUP BY g.id, g.code, g.name, g.address, g.domain
    """,
        nativeQuery = true
    )
    fun findGroupsWithMemberCount(@Param("keyword") keyword: String?): List<GroupProjection>

    @Query(
        """
    SELECT 
        g.code AS code,
        g.name AS groupName,
        g.address AS address,
        g.domain AS domain,
        COUNT(u.id) AS memberCount
        FROM groups g
        LEFT JOIN users u ON g.code = u.group_code
        WHERE (:name IS NULL OR LOWER(g.name) LIKE LOWER(CONCAT('%', :name, '%')))
        GROUP BY g.id, g.code, g.name, g.address, g.domain
        """,
        nativeQuery = true
    )
    fun findByNameContainingIgnoreCase(@Param("name") name: String?): List<GroupProjection>

    fun findByCode(groupCode: String): GroupEntity

}
