package com.example.apllicationgithubuser.Adapter

import com.example.apllicationgithubuser.data.remote.response.user

interface OnItemClickCallback {
    fun onItemClicked(user: user)
}