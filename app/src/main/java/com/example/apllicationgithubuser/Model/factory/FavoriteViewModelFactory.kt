package com.example.apllicationgithubuser.Model.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.apllicationgithubuser.Model.ViewModel.FavoriteViewModel
import com.example.apllicationgithubuser.Model.favoriteAdd.FavoriteAddViewModel

class FavoriteViewModelFactory(private val application: Application) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(application) as T
        } else if (modelClass.isAssignableFrom(FavoriteAddViewModel::class.java)) {
            return FavoriteAddViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }

    companion object {
        @Volatile
        private var INSTANCE: FavoriteViewModelFactory? = null

        @JvmStatic
        fun getInstance(application: Application): FavoriteViewModelFactory {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: FavoriteViewModelFactory(application).also { INSTANCE = it }
            }
        }
    }
}