package com.example.lio.Activities

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
//import android.support.v7.app.AppCompatActivity
//import android.os.Bundle
//import android.support.v4.widget.DrawerLayout
//import android.support.v7.app.ActionBarDrawerToggle
//import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.lio.Activities.Faculty.MyCoursesFaculty
import com.example.lio.Activities.Institute.Approvals
import com.example.lio.Activities.Student.MyCoursesStudent
import com.example.lio.Activities.Student.WishlistStudent
import com.example.lio.R
import com.google.android.material.navigation.NavigationView

open class BaseDrawer : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener
{

    lateinit var drawerLayout: DrawerLayout

    override fun setContentView(view: View?)
    {
        drawerLayout = layoutInflater.inflate(R.layout.base_drawer, null) as DrawerLayout

        var container : FrameLayout = drawerLayout.findViewById(R.id.activityContainer)
        container.addView(view)

        super.setContentView(drawerLayout)


        //adding toolbar
        var toolbar: Toolbar = drawerLayout.findViewById(R.id.toolBar) as Toolbar
        setSupportActionBar(toolbar)

        //adding navigation view
        var navigationView: NavigationView = drawerLayout.findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        //toggle
        var toggle : ActionBarDrawerToggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.menu_drawer_open, R.string.menu_drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean
    {
        drawerLayout.closeDrawer(GravityCompat.START)

        when(item.getItemId())
        {
            R.id.explore -> {
                startActivity(Intent(this, Explore::class.java))
                overridePendingTransition(0, 0)
            }
            R.id.my_courses -> {
                startActivity(Intent(this, MyCoursesStudent::class.java))
                overridePendingTransition(0, 0)
            }
            R.id.wishlist -> {
                startActivity(Intent(this, WishlistStudent::class.java))
                overridePendingTransition(0, 0)
            }
            R.id.my_courses_f -> {
            startActivity(Intent(this, MyCoursesFaculty::class.java))
            overridePendingTransition(0, 0)
            }
            R.id.approvals -> {
                startActivity(Intent(this, Approvals::class.java))
                overridePendingTransition(0, 0)
            }
            R.id.login -> {
                startActivity(Intent(this, SelectLogin::class.java))
                overridePendingTransition(0, 0)
            }
            R.id.profile -> {
                Toast.makeText(this, "profile", Toast.LENGTH_LONG).show()
            }
            R.id.logout -> {
                var shrdPref : SharedPreferences = getSharedPreferences("login_credentials", MODE_PRIVATE)
                var editor: SharedPreferences.Editor = shrdPref.edit()

                editor.clear()
                editor.apply()
                Toast.makeText(this, "You have been LoggedOut", Toast.LENGTH_LONG).show()

                startActivity(Intent(this, Explore::class.java))
            }
            R.id.share -> {
                Toast.makeText(this, "share", Toast.LENGTH_LONG).show()
            }
            R.id.rate -> {
                Toast.makeText(this, "rate", Toast.LENGTH_LONG).show()
            }
        }


        return false
    }

    @SuppressLint("RestrictedApi")
    protected fun allocateActivityTitle(titleString: String)
    {
        supportActionBar?.setTitle(titleString)
    }

}