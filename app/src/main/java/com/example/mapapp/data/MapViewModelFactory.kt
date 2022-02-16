package com.example.mapapp.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mapapp.Interface.MapResultCallBacks
import com.example.mspapp.Interface.LoginResultCallBacks

class MapViewModelFactory(private val listener: MapResultCallBacks):ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MapViewModel(listener) as T
    }
}