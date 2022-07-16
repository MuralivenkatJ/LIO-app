package com.example.lio.Activities.Faculty

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lio.Activities.BaseDrawer
import com.example.lio.Interfaces.MyCourses_Faculty
import com.example.lio.R
import com.example.lio.adapters.MyCourses_Faculty_RecyclerAdapter
import com.example.lio.databinding.MyCoursesFacultyBinding
import com.example.mycourses_lecturer.MyFaculty_MyData
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

    var layoutManager: RecyclerView.LayoutManager? = null
    var adapter: RecyclerView.Adapter<MyCourses_Faculty_RecyclerAdapter.ViewHolder>? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        //for menu bar
        binding = MyCoursesFacultyBinding.inflate(layoutInflater)
        allocateActivityTitle("My Courses")
        setContentView(R.layout.my_courses_faculty)
        //for menu bar

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

        val retrofitData = retrofitBuilder.getData()
        retrofitData.enqueue(object : Callback<MyFaculty_MyData?> {
            override fun onResponse(call: Call<MyFaculty_MyData?>, response: Response<MyFaculty_MyData?>) {
                val responseBody = response.body()!!
                adapter = MyCourses_Faculty_RecyclerAdapter(baseContext, responseBody.cours)
                recycler_View.adapter = adapter
            }

            override fun onFailure(call: Call<MyFaculty_MyData?>, t: Throwable) {

            }
        })
    }
}