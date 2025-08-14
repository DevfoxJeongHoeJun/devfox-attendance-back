package com.attend.attendance_api.dto

import com.attend.attendance_api.entity.UserEntity

class LoginResDto  {
    var id: Long? = null
    var accessLevelCode: String? = null
    var name: String? = null
    var groupCode: String? = null

    constructor(userEntity: UserEntity) {
        this.name = userEntity.name
        this.id = userEntity.id
        this.accessLevelCode = userEntity.accessLevelCode
        this.groupCode = userEntity.groupCode
    }
}