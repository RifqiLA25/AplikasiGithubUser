package com.example.apllicationgithubuser.Model.favoriteAdd

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.apllicationgithubuser.data.local.entity.UserEntity
import com.example.apllicationgithubuser.data.repository.UserRepository

class FavoriteAddViewModel(application: Application) : ViewModel() {

    private val mFavUserRepository: UserRepository = UserRepository(application)

    fun insert(user: UserEntity){
        mFavUserRepository.insert(user)
    }

    fun delete(user: UserEntity){
        mFavUserRepository.delete(user)
    }

}