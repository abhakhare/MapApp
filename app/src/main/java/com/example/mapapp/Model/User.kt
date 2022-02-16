package com.example.mapapp.Model


import android.text.TextUtils
import androidx.databinding.BaseObservable
import java.util.regex.Pattern

class User(private var mobilenumber: String, private var otp: String): BaseObservable() {


    fun isDataValid():Int{

        if (TextUtils.isEmpty(getMobilenumber()))
            return  0
        else if (!isValidMobileNo(getMobilenumber().toString().trim()))
            return  1
        else if (TextUtils.isEmpty(getOtp()))
            return  2
        else if (getOtp().length<5)
            return 3
        else
            return -1
    }

    fun isValidMobileNo(str: String): Boolean {
//(0/91): number starts with (0/91)
//[7-9]: starting of the number may contain a digit between 0 to 9
//[0-9]: then contains digits 0 to 9
        val ptrn = Pattern.compile("(0/91)?[7-9][0-9]{9}")
        //the matcher() method creates a matcher that will match the given input against this pattern
        val match = ptrn.matcher(str)
        //returns a boolean value
        return match.find() && match.group() == str
    }

    fun getOtp(): String{
        return  otp
    }

     fun getMobilenumber(): String{
        return  mobilenumber
    }

    fun setMobilenumber(mobilno: String){
        this.mobilenumber=mobilno
    }
    fun setOtp(otp: String){
        this.otp=otp
    }

}