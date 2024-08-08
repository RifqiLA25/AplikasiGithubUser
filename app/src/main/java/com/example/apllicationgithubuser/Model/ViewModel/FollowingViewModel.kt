package com.example.apllicationgithubuser.Model.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.apllicationgithubuser.data.remote.response.user
import com.example.apllicationgithubuser.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingViewModel : ViewModel() {


    private val _following = MutableLiveData<ArrayList<user>>()

    private val _getLoading = MutableLiveData<Boolean>()
    val getLoading: LiveData<Boolean> = _getLoading

    fun getSetFollowing(username: String) {
        ApiConfig.getApiService()
            .getUserFollowing(username)
            .enqueue(object : Callback<ArrayList<user>> {
                override fun onResponse(
                    call: Call<ArrayList<user>>,
                    response: Response<ArrayList<user>>
                ) {
                    if (response.isSuccessful) {
                        _following.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<user>>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                }

            })
    }

    fun following(): LiveData<ArrayList<user>> {
        return _following
    }
}
