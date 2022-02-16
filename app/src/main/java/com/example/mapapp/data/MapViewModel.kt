package com.example.mapapp.data

import androidx.lifecycle.ViewModel
import com.example.mapapp.Interface.MapResultCallBacks
import com.example.mapapp.Model.PlacesModel
import com.example.mapapp.Model.User
import com.example.mspapp.Interface.LoginResultCallBacks

class MapViewModel(private val listener: MapResultCallBacks): ViewModel() {

    private val placesModel: PlacesModel



    init {
        this.placesModel = PlacesModel("","",0.0,0.0)
        this.listener
    }
   /* init {

    }*/

}
