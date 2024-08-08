package com.example.apllicationgithubuser.Model.ViewModel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.apllicationgithubuser.data.remote.response.DetailUserResponse
import com.example.apllicationgithubuser.data.remote.response.ResponseUserSaya
import com.example.apllicationgithubuser.data.remote.response.user
import com.example.apllicationgithubuser.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val _users = MutableLiveData<ArrayList<user>>()


    fun searchUsers(query: String) {
        ApiConfig.getApiService()
            .searchUsers(query)
            .enqueue(object : Callback<ResponseUserSaya> {
                override fun onResponse(
                    call: Call<ResponseUserSaya>,
                    response: Response<ResponseUserSaya>
                ) {
                    if (response.isSuccessful) {
                        _users.postValue(response.body()?.items)
                    }
                }

                override fun onFailure(call: Call<ResponseUserSaya>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                }

            })
    }

    fun Users(): LiveData<ArrayList<user>> {
        return _users
    }
}