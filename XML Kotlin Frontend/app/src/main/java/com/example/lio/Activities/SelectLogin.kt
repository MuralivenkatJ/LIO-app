package com.example.lio.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.lio.Activities.Faculty.FacultyLogin
import com.example.lio.Activities.Institute.InstituteLogin
import com.example.lio.Activities.Student.StudentLogin
import com.example.lio.R
import com.example.lio.databinding.SelectLoginBinding

class SelectLogin : BaseDrawer()
{
    //for menu bar
    lateinit var binding: SelectLoginBinding

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        //for menu bar
        binding = SelectLoginBinding.inflate(layoutInflater)
        allocateActivityTitle("Login")
        setContentView(binding.root)
        //for menu bar
    }

    fun studentLogin(v: View?)
    {
        var i = Intent(this, StudentLogin::class.java)
        startActivity(i)
    }

    fun facultyLogin(v: View?)
    {
        var i = Intent(this, FacultyLogin::class.java)
        startActivity(i)
    }

    fun instituteLogin(v: View?)
    {
        var i = Intent(this, InstituteLogin::class.java)
        startActivity(i)
    }

}
