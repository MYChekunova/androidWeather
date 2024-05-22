package com.example.lab1

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User (
    @PrimaryKey(autoGenerate = true)
    var userId: Long = 0L,
    @ColumnInfo(name = "user_login")
    var userLogin: String = "",
    @ColumnInfo(name = "user_password")
    var userPassword: String = ""
    )