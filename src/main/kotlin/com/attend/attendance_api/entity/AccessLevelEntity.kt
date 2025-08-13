package com.attend.attendance_api.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "access_level")
class AccessLevelEntity(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false)
    var code: String,

    @Column(nullable = false)
    var name: String,

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(insertable = false)
    var createdUser: Long,

    @UpdateTimestamp
    @Column(insertable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now(),

    @Column(insertable = false)
    var updatedUser: Long,
)