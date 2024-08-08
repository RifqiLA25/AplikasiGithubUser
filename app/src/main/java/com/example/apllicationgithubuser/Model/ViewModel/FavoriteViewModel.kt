package com.example.apllicationgithubuser.Model.ViewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.apllicationgithubuser.data.local.entity.UserEntity
import com.example.apllicationgithubuser.data.repository.UserRepository

class FavoriteViewModel(application: Application) : ViewModel() {

    private val mFavUserRepository: UserRepository = UserRepository(application)

    fun getFavoriteUser(): LiveData<List<UserEntity>> = mFavUserRepository.getAllUser()
    fun getFav(username: String): LiveData<List<UserEntity>> = mFavUserRepository.getFavUserByUsername(username)

}