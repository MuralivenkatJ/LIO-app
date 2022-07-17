package com.example.lio.Activities.Student

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lio.Activities.BaseDrawer
import com.example.lio.Activities.Explore
import com.example.lio.Interfaces.MyCourses_Student
import com.example.lio.Models.Student.MyStudentData
import com.example.lio.R
import com.example.lio.adapters.MyCourses_Student_RecyclerAdapter
import com.example.lio.databinding.MyCoursesStudentBinding
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.my_courses_student.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://learn-it-online.herokuapp.com/"

class MyCoursesStudent : BaseDrawer()
{

    //for menu bar
    lateinit var binding : MyCoursesStudentBinding

    var accessToken: String? = null

    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var adapter: RecyclerView.Adapter<MyCourses_Student_RecyclerAdapter.ViewHolder>


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        //for menu bar
        binding = MyCoursesStudentBinding.inflate(layoutInflater)
        allocateActivityTitle("My Courses")
        setContentView(binding.root)
        //for menu bar

        //updating the menu bar
        var navigationView = findViewById(R.id.nav_view) as NavigationView
        var menu: Menu = navigationView.menu

        menu.findItem(R.id.my_courses_f).setVisible(false)
        menu.findItem(R.id.approvals).setVisible(false)
        menu.findItem(R.id.login).setVisible(false)
        //updating the menu bar

        //getting the accessToken
        var shrdPref: SharedPreferences = getSharedPreferences("login_credentials", MODE_PRIVATE)
        accessToken = shrdPref.getString("accessToken", "None")
        if(accessToken == "None" || accessToken == null)
        {
            var editor: SharedPreferences.Editor = shrdPref.edit()
            editor.clear()
            editor.apply()

            Toast.makeText(this, "You have to log in", Toast.LENGTH_LONG).show()
            startActivity(Intent(this, Explore::class.java))
            return
        }


        layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        getMyData()
    }

    private fun getMyData()
    {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(MyCourses_Student::class.java)

        val retrofitData = retrofitBuilder.getData("Bearer " + accessToken)

        retrofitData.enqueue(object : Callback<MyStudentData>
        {
            override fun onResponse(call: Call<MyStudentData>, response: Response<MyStudentData>?)
            {
                val responseBody = response?.body()!!
                adapter = MyCourses_Student_RecyclerAdapter(baseContext, response.body()!!.enrolled)
                recyclerView.adapter = adapter

            }

            override fun onFailure(call: Call<MyStudentData>, t: Throwable)
            {

            }

        })


    }


    fun goToPlayerPage(v: View?)
    {
        var i = Intent(this, VideoPlayer::class.java)
        i.putExtra("accessToken", accessToken)
        startActivity(i)
    }

}
