package com.example.apllicationgithubuser.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.apllicationgithubuser.data.local.entity.UserEntity
import com.example.apllicationgithubuser.data.local.room.UserDao
import com.example.apllicationgithubuser.data.local.room.UserDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class UserRepository(application: Application) {


    private val mUserDao: UserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = UserDatabase.getDatabase(application)
        mUserDao = db.userDao()
    }

    fun getAllUser(): LiveData<List<UserEntity>> = mUserDao.getAllUser()

    fun insert(favUser: UserEntity) {
        executorService.execute { mUserDao.insert(favUser) }
    }

    fun delete(favUser: UserEntity) {
        executorService.execute { mUserDao.delete(favUser) }
    }
    fun getFavUserByUsername(username: String): LiveData<List<UserEntity>> = mUserDao.getFavUserByUsername(username)
}