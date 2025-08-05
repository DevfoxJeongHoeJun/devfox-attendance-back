package com.attend.attendance_api.entity

import jakarta.persistence.*
import jakarta.validation.constraints.Email
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "users")
class UserEntity(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "access_level_code", nullable = false)
    var accessLevel: AccessLevelEntity,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_code", nullable = false)
    var group: GroupEntity,

    @Column(nullable = false)
    var name: String,

    @Column(nullable = false, length = 100)
    @Email
    var email: String,

    @Column(nullable = false)
    private var password: String,

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_user")
    var createdUser: UserEntity? = null,

    @UpdateTimestamp
    @Column(insertable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_user")
    var updatedUser: UserEntity? = null,

    )