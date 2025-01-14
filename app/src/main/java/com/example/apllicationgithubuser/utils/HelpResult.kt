package com.example.apllicationgithubuser.utils

sealed class HelpResult<out R> private constructor() {
    data class Success<out T>(val data: T) : HelpResult<T>()
    data class Error(val error: String) : HelpResult<Nothing>()
    object Loading : HelpResult<Nothing>()
}