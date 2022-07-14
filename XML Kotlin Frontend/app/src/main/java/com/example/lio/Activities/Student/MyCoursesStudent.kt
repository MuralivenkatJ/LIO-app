package com.example.lio.Activities.Student

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lio.Interfaces.MyCourses_Student
import com.example.lio.Models.Student.MyStudentData
import com.example.lio.R
import com.example.lio.adapters.MyCourses_Student_RecyclerAdapter
import kotlinx.android.synthetic.main.my_courses_student.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://learn-it-online.herokuapp.com/"

class MyCoursesStudent : AppCompatActivity() {

    var layoutManager: RecyclerView.LayoutManager? = null
    var adapter: RecyclerView.Adapter<MyCourses_Student_RecyclerAdapter.ViewHolder>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_courses_student)

        getMyData()

        layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
    }

    private fun getMyData() {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(MyCourses_Student::class.java)

        val retrofitData = retrofitBuilder.getData()

        retrofitData.enqueue(object : Callback<MyStudentData> {
            override fun onResponse(call: Call<MyStudentData>, response: Response<MyStudentData>) {
                val responseBody = response.body()!!
                adapter = MyCourses_Student_RecyclerAdapter(baseContext, responseBody.myCoursesEnrolled)
                recyclerView.adapter = adapter

            }

            override fun onFailure(call: Call<MyStudentData>, t: Throwable) {

            }

        })


    }


}
