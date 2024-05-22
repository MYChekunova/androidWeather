package com.example.lab1

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import org.jetbrains.annotations.Async.Execute

class UserViewModel(application: Application): AndroidViewModel(application) {
    var newUserLogin = ""
    var newUserPassword = ""
    val dao =
        UserDatabase.getInstance(context = application).userDAO
    fun addUser(){
        viewModelScope.launch{
            val user = User()
            user.userLogin = newUserLogin
            user.userPassword = newUserPassword
            dao.insert(user)
        }
    }

    fun allUsers(): List<User>{
        val us = runBlocking { async { dao.getAll() } }
        val users =  runBlocking { us.await() }
        return users
    }
}