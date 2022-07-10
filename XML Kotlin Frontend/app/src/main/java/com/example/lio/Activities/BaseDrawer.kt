package com.example.lio.Activities

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import com.example.lio.R
import com.google.android.material.navigation.NavigationView

open class BaseDrawer : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var drawerLayout: DrawerLayout

    override fun setContentView(view: View?)
    {
        drawerLayout = layoutInflater.inflate(R.layout.base_drawer, null) as DrawerLayout

        var container : FrameLayout = drawerLayout.findViewById(R.id.activityContainer)
        container.addView(view)

        super.setContentView(drawerLayout)

        var toolbar: Toolbar = drawerLayout.findViewById(R.id.toolBar) as Toolbar
        setSupportActionBar(toolbar)

        var navigationView: NavigationView = drawerLayout.findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        var toogle : ActionBarDrawerToggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.menu_drawer_open, R.string.menu_drawer_close)
        drawerLayout.addDrawerListener(toogle)
        toogle.syncState()
    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean
    {
        return false
    }

    @SuppressLint("RestrictedApi")
    protected fun allocateActivityTitle(titleString: String)
    {
        supportActionBar?.setWindowTitle(titleString)
    }

}