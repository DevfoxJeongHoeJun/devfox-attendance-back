package com.attend.attendance_api.repository

import com.attend.attendance_api.entity.GroupEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface GroupRepository: JpaRepository<GroupEntity, Long> {

    // グループ登録する時、グループコード生成のために、DBからMAX（一番高い）コードを取得
    @Query(value = """
        SELECT COALESCE(
            MAX(CAST(SUBSTRING(code, 2, LENGTH(code)) AS INTEGER)),
            0) + 1 AS group_code
        FROM public.groups
    """, nativeQuery = true)
    fun getMaxGroupCode(): Int
}