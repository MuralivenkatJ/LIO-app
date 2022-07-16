package com.example.lio.Activities.Faculty

//import android.support.v7.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.lio.Helpers.ServiceBuilder
import com.example.lio.Interfaces.Faculty
import com.example.lio.Models.Login.Login
import com.example.lio.Models.Login.LoginResponse
//import com.example.login.Models.FacultyLoginResponse
//import com.example.login.Models.FacultyLogin
//import com.example.login.R
//import com.example.login.ServiceBuilder.Faculty
//import com.example.login.ServiceBuilder.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.lio.R

class FacultyLogin : AppCompatActivity()
{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.faculty_login)
    }

    fun register(v: View?)
    {
        var i = Intent(this, FacultyRegister::class.java)
        startActivity(i)
    }

    fun login(v: View?)
    {

        var email = findViewById<EditText>(R.id.emaliid).toString()
        var password = findViewById<EditText>(R.id.password).toString()

        var f_l = Login(email, password)

        var serviceBuilderr = ServiceBuilder.buildService(Faculty::class.java)
        var requestCall = serviceBuilderr.login(f_l)

        requestCall.enqueue(object: Callback<LoginResponse>{
            override fun onResponse(call: Call<LoginResponse>?, response: Response<LoginResponse>?)
            {
                if (response != null)
                {
                    if(response.isSuccessful)
                    {
                        var test = findViewById<TextView>(R.id.test)
                        test.setText(response.body()!!.accessToken.toString())
                        Toast.makeText(this@FacultyLogin, response.body()!!.accessToken, Toast.LENGTH_LONG).show()
                    }
                    else
                        Toast.makeText(this@FacultyLogin, "response is not successful", Toast.LENGTH_LONG).show()
                }
                else
                    Toast.makeText(this@FacultyLogin, "null response", Toast.LENGTH_LONG).show()
            }

            override fun onFailure(call: Call<LoginResponse>?, t: Throwable?) {
                var test = findViewById<TextView>(R.id.test)
                test.setText("Failed to get response")
                Toast.makeText(this@FacultyLogin, "failed", Toast.LENGTH_LONG).show()
            }

        })
    }


}