package com.example.lio.Activities.Faculty

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lio.Activities.BaseDrawer
import com.example.lio.Activities.Explore
import com.example.lio.Interfaces.MyCourses_Faculty
import com.example.lio.R
import com.example.lio.adapters.MyCourses_Faculty_RecyclerAdapter
import com.example.lio.databinding.MyCoursesFacultyBinding
import com.example.mycourses_lecturer.MyFaculty_MyData
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.my_courses_faculty.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL="https://learn-it-online.herokuapp.com/"

class MyCoursesFaculty : BaseDrawer()
{
    //for menu bar
    lateinit var binding: MyCoursesFacultyBinding
    lateinit var navigationView : NavigationView
    lateinit  var menu: Menu

    var layoutManager: RecyclerView.LayoutManager? = null
    var adapter: RecyclerView.Adapter<MyCourses_Faculty_RecyclerAdapter.ViewHolder>? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        //for menu bar
        binding = MyCoursesFacultyBinding.inflate(layoutInflater)
        allocateActivityTitle("My Courses")
        setTitle("My Courses")
        setContentView(binding.root)
        //for menu bar

        //updating the menu bar
        navigationView = findViewById(R.id.nav_view) as NavigationView
        menu = navigationView.menu as Menu

        menu.findItem(R.id.my_courses).setVisible(false)
        menu.findItem(R.id.wishlist).setVisible(false)
        menu.findItem(R.id.approvals).setVisible(false)
        menu.findItem(R.id.login).setVisible(false)
        //updating the menu bar

        getMyData()


        layoutManager = LinearLayoutManager(this)
        recycler_View.layoutManager = layoutManager
    }

    private fun getMyData() {
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MyCourses_Faculty::class.java)

        //getting the accessToken
        var shrdPref: SharedPreferences = getSharedPreferences("login_credentials", MODE_PRIVATE)
        var accessToken = shrdPref.getString("accessToken", "None")
        if(accessToken == "None" || accessToken == null)
        {
            var editor: SharedPreferences.Editor = shrdPref.edit()
            editor.clear()
            editor.apply()

            Toast.makeText(this, "You have to login", Toast.LENGTH_LONG).show()
            startActivity(Intent(this, Explore::class.java))
            return
        }

        val retrofitData = retrofitBuilder.getData("Bearer " + accessToken)


        retrofitData.enqueue(object : Callback<MyFaculty_MyData?> {
            override fun onResponse(call: Call<MyFaculty_MyData?>, response: Response<MyFaculty_MyData?>) {
                val responseBody = response.body()!!

                //to check if the student is logged in
                if(responseBody.courses == null)
                {
                    var msg = responseBody.msg
                    if(msg == "Token expired. You have to login again.")
                    {
                        Toast.makeText(this@MyCoursesFaculty, msg, Toast.LENGTH_LONG).show()
                        menu.performIdentifierAction(R.id.logout,0)
                        return
                    }
                    Toast.makeText(this@MyCoursesFaculty, msg, Toast.LENGTH_LONG).show()
                    startActivity(Intent(this@MyCoursesFaculty, Explore::class.java))
                    return
                }

                adapter = MyCourses_Faculty_RecyclerAdapter(baseContext, responseBody.courses)
                recycler_View.adapter = adapter
            }

            override fun onFailure(call: Call<MyFaculty_MyData?>, t: Throwable) {

            }
        })
    }


    fun uploadCourse(v: View?)
    {
        startActivity(Intent(this@MyCoursesFaculty, UploadCourse::class.java))
    }


}