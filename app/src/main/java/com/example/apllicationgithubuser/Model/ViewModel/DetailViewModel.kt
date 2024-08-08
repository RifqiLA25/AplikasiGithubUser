package com.example.apllicationgithubuser.Model.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.apllicationgithubuser.data.remote.response.DetailUserResponse
import com.example.apllicationgithubuser.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {

    private val _detailUser = MutableLiveData<DetailUserResponse>()

    fun setDetailUsers(username: String) {
        ApiConfig.getApiService()
            .getUserDetail(username)
            .enqueue(object : Callback<DetailUserResponse> {
                override fun onResponse(
                    call: Call<DetailUserResponse>,
                    response: Response<DetailUserResponse>
                ) {
                    if (response.isSuccessful) {
                        _detailUser.postValue(response.body())
                        Log.d("DetailViewModel", "Detail user response received: ${response.body()}")
                    } else {
                        Log.e("DetailViewModel", "Failed to get detail user, response code: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                    Log.e("DetailViewModel", "Failed to get detail user", t)
                }

            })
    }

    fun detailUser(): LiveData<DetailUserResponse> {
        return _detailUser
    }
}