package com.attend.attendance_api.entity

import jakarta.persistence.*
import jakarta.validation.constraints.Email
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "users")
data class UserEntity(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false)
    var accessLevelCode: String,

    @Column(nullable = false)
    var groupCode: String,

    @Column(nullable = false)
    var name: String,

    @Column(nullable = false, length = 100)
    @Email
    var email: String,

    @Column(nullable = false)
    var password: String,

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @UpdateTimestamp
    @Column(insertable = false)
    var updatedAt: LocalDateTime? = LocalDateTime.now(),

    @Column(insertable = false)
    var updatedUser: Long? = null,

    @ManyToOne
    @JoinColumn(name = "group_id")
    val group: GroupEntity? = null,

    )