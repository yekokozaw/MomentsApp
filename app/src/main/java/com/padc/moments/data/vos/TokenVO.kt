package com.padc.moments.data.vos

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "token_table")
data class TokenVO(

    @ColumnInfo(name = "token")
    val token : String?,

    @ColumnInfo(name = "email")
    val email : String?,

    @PrimaryKey
    val id : Int? = 1
)
