package com.example.lio.Activities.Student

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
import com.example.lio.Interfaces.Student
import com.example.lio.Models.Login.Login
import com.example.lio.Models.Login.LoginResponse
//import com.example.login.Models.StudentLogin
//import com.example.login.Models.StudentLoginResponse
//import com.example.login.R
//import com.example.login.ServiceBuilder.ServiceBuilder
//import com.example.login.ServiceBuilder.Student
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.lio.R

class StudentLogin : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.student_login)
    }

    fun register(v: View?)
    {
        var i = Intent(this, StudentRegister::class.java)
        startActivity(i)
    }

    fun login(v: View?)
    {
        var email = findViewById<EditText>(R.id.emailid).text.toString()
        var password = findViewById<EditText>(R.id.password).text.toString()

        var serviceBuilderr = ServiceBuilder.buildService(Student::class.java)
        var requestCall = serviceBuilderr.studentLogin(email, password)

        requestCall.enqueue(object: Callback<LoginResponse>
        {
            override fun onResponse(call: Call<LoginResponse>?, response: Response<LoginResponse>?)
            {

                if (response != null)
                {
                    Toast.makeText(this@StudentLogin, response.body()!!.msg, Toast.LENGTH_LONG).show()

                    if(response.body()!!.msg == "Logged in Successfully")
                    {
                        //shared preferences
                        var shrdPref: SharedPreferences =
                            getSharedPreferences("login_credentials", MODE_PRIVATE)
                        var editor: SharedPreferences.Editor = shrdPref.edit()

                        editor.putString("loggedInAs", "student")
                        editor.putString("accessToken", response.body()!!.accessToken)

                        editor.apply()
                        //shared preferences

                        //going back to explore page
                        startActivity(Intent(this@StudentLogin, Explore::class.java))
                    }
                }
            }

            override fun onFailure(call: Call<LoginResponse>?, t: Throwable?)
            {
                Toast.makeText(this@StudentLogin, "Inside On Failure", Toast.LENGTH_LONG).show()
            }

        })
    }


}