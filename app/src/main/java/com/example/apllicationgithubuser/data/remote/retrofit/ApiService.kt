package com.example.apllicationgithubuser.data.remote.retrofit


import com.example.apllicationgithubuser.BuildConfig
import com.example.apllicationgithubuser.data.remote.response.DetailUserResponse
import com.example.apllicationgithubuser.data.remote.response.ResponseUserSaya
import com.example.apllicationgithubuser.data.remote.response.user
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    @Headers("Authorization: token ${BuildConfig.GITHUB_TOKEN}")
    fun searchUsers(
        @Query("q") q: String
    ): Call<ResponseUserSaya>

    @GET("users/{username}")
    @Headers("Authorization: token ${BuildConfig.GITHUB_TOKEN}")
    fun getUserDetail(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token ${BuildConfig.GITHUB_TOKEN}")
    fun getUserFollowers(
        @Path("username") username: String
    ): Call<ArrayList<user>>

    @GET("users/{username}/following")
    @Headers("Authorization: token ${BuildConfig.GITHUB_TOKEN}")
    fun getUserFollowing(
        @Path("username") username: String
    ): Call<ArrayList<user>>

}