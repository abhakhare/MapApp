package com.example.mapapp.data


import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel

import com.example.mapapp.Model.User
import com.example.mspapp.Interface.LoginResultCallBacks
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider


class LoginViewModel (private val listener:LoginResultCallBacks):ViewModel(){
    private val user:User



    init {
        this.user = User("","")
    }


    //create function to set Email after user finish enter text
    val mobilenoTextWatcher:TextWatcher
                get()= object:TextWatcher{
                    override fun afterTextChanged(s: Editable?) {
                        user.setMobilenumber(s.toString())
                        var loginCode:Int = user.isDataValid()

                        if(loginCode==-1){

                        }else{


                        }


                    }

                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                    }

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                    }

    }

    val otpTextWatcher:TextWatcher
        get()= object:TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                user.setOtp(s.toString())



            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        }




    //create function to process Login Button clicked
    fun onLoginClicked(v: View){

        var loginCode:Int = user.isDataValid()
        if (loginCode == 0){
            listener.onError("Please enter your mobile number")}
        else if (loginCode == 1){

            listener.onError("Please enter valid number")}
       /* else if (loginCode == 2){

            listener.onError("Please enter otp")}
        else if (loginCode == 3){

            listener.onError("otp not meeting the criteria")}*/

        else{

            listener.onSuccess("",user.getMobilenumber())
        }
    }

    fun onLoginClickedbtn(v: View){
        var loginCode:Int = user.isDataValid()

          if (loginCode == 2){

          listener.onError("Please enter otp")}
      else if (loginCode == 3){

          listener.onError("otp not meeting the criteria")}

        else{
            //user.setOtp(user.getOtp())

            listener.onSuccessbtnvrify()}
        }

}


