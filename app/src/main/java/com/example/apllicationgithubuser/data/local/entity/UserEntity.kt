package com.example.apllicationgithubuser.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "user")
@Parcelize
data class UserEntity(
    @PrimaryKey(autoGenerate = false)

    @ColumnInfo(name = "username")
    var username: String = " ",

    @ColumnInfo(name = "avatar_URL")
    var avatarUrl: String? = null
) : Parcelable