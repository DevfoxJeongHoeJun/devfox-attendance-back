package com.attend.attendance_api.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "attendance")
class AttendEntity(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false)
    var userId: Long,

    @Column(nullable = false)
    var date: LocalDate,

    @Column(nullable = false)
    var type: String,

    @Column(nullable = false)
    var startTime: LocalDateTime,

    var startLocation: String,

    var endTime: LocalDateTime? = null,

    var endLocation: String,

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(insertable = false)
    var createdUser: Long,

    @UpdateTimestamp
    @Column(insertable = false)
    var updatedAt: LocalDateTime? = LocalDateTime.now(),

    @Column(insertable = false)
    var updatedUser: Long? = null,
)