package com.example.lio.Activities.Student

//import android.support.v7.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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

class StudentLogin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
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
        var f_l = Login()

        f_l.email = findViewById<EditText>(R.id.emailid).toString()
        f_l.password = findViewById<EditText>(R.id.password).toString()

        var serviceBuilderr = ServiceBuilder.buildService(Student::class.java)
        var requestCall = serviceBuilderr.studentLogin(f_l)

        requestCall.enqueue(object: Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>?, response: Response<LoginResponse>?)
            {
//                if (response != null)
//                {
//                    if(response.isSuccessful)
//                    {
                        var test = findViewById<TextView>(R.id.test)
                if (response != null) {
                    test.text = response.body()!!.accessToken
                }
//                    }
//                }
            }

            override fun onFailure(call: Call<LoginResponse>?, t: Throwable?) {
                var test = findViewById<TextView>(R.id.test)
                test.setText("Failed to get response")
            }

        })
    }


}