package com.example.lio.Activities.Student

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.login.Models.StudentLogin
import com.example.login.Models.StudentLoginResponse
import com.example.login.R
import com.example.login.ServiceBuilder.ServiceBuilder
import com.example.login.ServiceBuilder.Student
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.lio.R

class StudentLogin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.student_login)
    }
   fun validate(v: View?)
    {
        val email_pattern = Regex("[a-z0-9]+@[a-z]+(\\.[a-z]+)+")
        val password_pattern = Regex("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{6,10}$")

        var email_field = findViewById(R.id.emailid) as EditText
        var password_field = findViewById(R.id.password) as EditText

        var email = email_field.text.toString().trim()
        var password = password_field.text.toString().trim()

//        Toast.makeText(this, email, Toast.LENGTH_LONG).show()

        if(email.isEmpty())
        {
            Toast.makeText(this, "Email required", Toast.LENGTH_LONG).show()
            email_field.error = "This field is required"
            return
        }

        if( !email_pattern.matches(email) )
        {
            email_field.error = "This email is not valid"
            Toast.makeText(this, "Email Invalid", Toast.LENGTH_LONG).show()
            return
        }


        if(password.isEmpty())
        {
            Toast.makeText(this, "Password Required", Toast.LENGTH_LONG).show()
            password_field.error = "This field is required"
            return
        }
        if( !password_pattern.matches(password) )
        {
            password_field.error = "This password is not strong"
            Toast.makeText(this, "Password Invalid", Toast.LENGTH_LONG).show()
            return
        }

    }

    fun login(v: View?)
    {
        var f_l = StudentLogin()

        f_l.email = findViewById<EditText>(R.id.emailid).toString()
        f_l.password = findViewById<EditText>(R.id.password).toString()

        var serviceBuilderr = ServiceBuilder.buildService(Student::class.java)
        var requestCall = serviceBuilderr.login(f_l)

        requestCall.enqueue(object: Callback<StudentLoginResponse> {
            override fun onResponse(call: Call<StudentLoginResponse>?, response: Response<StudentLoginResponse>?)
            {
//                if (response != null)
//                {
//                    if(response.isSuccessful)
//                    {
                        var test = findViewById<TextView>(R.id.test)
                if (response != null) {
                    test.text = response.body().accessToken
                }
//                    }
//                }
            }

            override fun onFailure(call: Call<StudentLoginResponse>?, t: Throwable?) {
                var test = findViewById<TextView>(R.id.test)
                test.setText("Failed to get response")
            }

        })
    }


}