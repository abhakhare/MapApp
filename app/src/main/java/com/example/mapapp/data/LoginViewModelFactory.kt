package com.example.mapapp.data


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.example.mspapp.Interface.LoginResultCallBacks


class LoginViewModelFactory (private val listener: LoginResultCallBacks):ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LoginViewModel(listener) as T
    }
}