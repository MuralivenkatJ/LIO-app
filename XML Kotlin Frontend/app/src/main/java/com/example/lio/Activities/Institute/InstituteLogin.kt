package com.example.lio.Activities.Institute

//import android.support.v7.app.AppCompatActivity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.lio.Activities.Explore
import com.example.lio.Helpers.ServiceBuilder
import com.example.lio.Interfaces.Institute
import com.example.lio.Models.Login.Login
import com.example.lio.Models.Login.LoginResponse
//import com.example.login.Models.InstituteLogin
//import com.example.login.Models.InstituteLoginResponse
//import com.example.login.R
//import com.example.login.ServiceBuilder.Institute
//import com.example.login.ServiceBuilder.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.lio.R

class InstituteLogin : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.institute_login)
    }

    fun register(v: View?)
    {
        var i = Intent(this, InstituteRegister::class.java)
        startActivity(i)
    }

    fun login(v: View?)
    {
        var email = findViewById<EditText>(R.id.emaliid).text.toString()
        var password = findViewById<EditText>(R.id.password).text.toString()

        var serviceBuilderr = ServiceBuilder.buildService(Institute::class.java)
        var requestCall = serviceBuilderr.instituteLogin(email, password)

        requestCall.enqueue(object: Callback<LoginResponse>
        {
            override fun onResponse(call: Call<LoginResponse>?, response: Response<LoginResponse>?)
            {
                if (response != null)
                {
                    Toast.makeText(this@InstituteLogin, response.body()!!.msg, Toast.LENGTH_LONG).show()

                    if(response.body()!!.msg == "Logged in Successfully")
                    {
                        //shared preferences
                        var shrdPref: SharedPreferences =
                            getSharedPreferences("login_credentials", MODE_PRIVATE)
                        var editor: SharedPreferences.Editor = shrdPref.edit()

                        editor.putString("loggedInAs", "institute")
                        editor.putString("accessToken", response.body()!!.accessToken)

                        editor.apply()
                        //shared preferences

                        //going back to explore page
                        startActivity(Intent(this@InstituteLogin, Explore::class.java))
                    }
                }
            }

            override fun onFailure(call: Call<LoginResponse>?, t: Throwable?)
            {

            }

        })
    }

}