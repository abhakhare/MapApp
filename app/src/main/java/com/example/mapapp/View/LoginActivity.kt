package com.example.mapapp.View




import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.set
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.mapapp.Model.User
import com.example.mapapp.R
import com.example.mapapp.data.LoginViewModel
import com.example.mapapp.data.LoginViewModelFactory
import com.example.mapapp.databinding.ActivityLoginBinding
import com.example.mspapp.Interface.LoginResultCallBacks
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import java.util.concurrent.TimeUnit


class LoginActivity: AppCompatActivity() , LoginResultCallBacks {
    lateinit var activityLoginBinding : ActivityLoginBinding
    lateinit var auth: FirebaseAuth
    lateinit var storedVerificationId:String
    lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    var number : String =""
    var msg : String =""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
     //  setContentView(R.layout.activity_login)

         activityLoginBinding = DataBindingUtil.setContentView<ActivityLoginBinding>(
             this,
             R.layout.activity_login
         )
         activityLoginBinding.loginviewModel= ViewModelProviders.of(
             this,
             LoginViewModelFactory(this)
         ).get(LoginViewModel::class.java)
        auth=FirebaseAuth.getInstance()
        // Callback function for Phone Auth
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.

                Log.d("TAG", "onVerificationCompleted:$credential")
                val code = credential.getSmsCode().toString();
                activityLoginBinding.idEdtOtp.setText(code)
        val toast = Toast.makeText(applicationContext, " Otp send successful ", Toast.LENGTH_SHORT)
                toast.show()

            }

            override fun onVerificationFailed(e: FirebaseException) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w("TAG", "onVerificationFailed", e)
                val toast = Toast.makeText(applicationContext, " Verification failed, This number is used too many times for verification limit ", Toast.LENGTH_SHORT)
                toast.show()

                if (e is FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                } else if (e is FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                }

                // Show a message and update the UI
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d("TAG", "onCodeSent:$verificationId")

                // Save verification ID and resending token so we can use them later
                storedVerificationId = verificationId
                resendToken = token
                activityLoginBinding.idEdtPhoneNumber.text.clear();
                activityLoginBinding.idEdtPhoneNumber.visibility= View.GONE
                activityLoginBinding.idBtnGetOtp.visibility= View.GONE
                activityLoginBinding.idEdtOtp.visibility=View.VISIBLE
                activityLoginBinding.idBtnVerify.visibility=View.VISIBLE


            }
        }
    }




    override fun onSuccess(message: String, user: String) {
        msg=message
        number = "+91$user"
        sendVerificationCode(number)

    }
    override fun onSuccessbtnvrify() {

        val code=activityLoginBinding.idEdtOtp.text.toString()

        Log.d("TAG", "signInWithCredential:success"+code)
        val credential = PhoneAuthProvider.getCredential(storedVerificationId,
            code)
        signInWithPhoneAuthCredential(credential)
    }
    override fun onError(message: String) {
        val toast = Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT)
        toast.show()

    }
    private fun sendVerificationCode(number: String) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(number)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this)                 // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
        Log.d("GFG", "Auth started")
    }


    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("TAG", "signInWithCredential:success")
                    val toast = Toast.makeText(applicationContext, "Login has been successful", Toast.LENGTH_SHORT)
                    toast.show()

                    val user = task.result?.user
                    val intent= Intent(applicationContext,MapsActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // Sign in failed, display a message and update the UI
                    Log.w("TAG", "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                    }
                    // Update UI
                }
            }
    }
}