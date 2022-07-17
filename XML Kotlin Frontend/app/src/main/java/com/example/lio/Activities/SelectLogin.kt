package com.example.lio.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Button
import com.example.lio.Activities.Faculty.FacultyLogin
import com.example.lio.Activities.Institute.InstituteLogin
import com.example.lio.Activities.Student.StudentLogin
import com.example.lio.R
import com.example.lio.databinding.SelectLoginBinding
import com.google.android.material.navigation.NavigationView

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

        //updating the menu bar
        var navigationView = findViewById(R.id.nav_view) as NavigationView
        var menu: Menu = navigationView.menu

        menu.findItem(R.id.my_courses).setVisible(false)
        menu.findItem(R.id.wishlist).setVisible(false)
        menu.findItem(R.id.my_courses_f).setVisible(false)
        menu.findItem(R.id.approvals).setVisible(false)
        menu.findItem(R.id.profile).setVisible(false)
        menu.findItem(R.id.logout).setVisible(false)
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
