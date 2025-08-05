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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    var userId: UserEntity,

    @Column(nullable = false)
    var date: LocalDate,

    @Column(nullable = false)
    var type: String,

    @Column(nullable = false)
    var startTime: LocalDateTime,

    @Column(nullable = false)
    var startLocation: String,

    var endTime: LocalDateTime? = null,

    var endLocation: String,

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_user", nullable = false)
    var createdUser: UserEntity,

    @UpdateTimestamp
    @Column(insertable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_user", nullable = false)
    var updatedUser: UserEntity
)