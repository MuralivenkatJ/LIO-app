package com.example.lio.Activities

//import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.lio.R

import android.media.Image
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.lio.Interfaces.UnenrollAppInterface
import com.example.lio.Models.UnenrollDataC
import com.squareup.picasso.Picasso
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL="https://learn-it-online.herokuapp.com/"

class UnenrolledCourse : AppCompatActivity()
{

    lateinit var c_image: ImageView
    lateinit var course_name: TextView
    lateinit var desc : TextView
    lateinit var f_image: ImageView
    lateinit var faculty: TextView
    lateinit var i_image: ImageView
    lateinit var i_name: TextView
    lateinit var skills: TextView




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.unenrolled_course)


        c_image = findViewById(R.id.c_image)
        course_name = findViewById(R.id.course_name) as TextView
        desc = findViewById(R.id.desc) as TextView
        f_image = findViewById(R.id.f_image) as ImageView
        faculty = findViewById(R.id.faculty) as TextView
        i_image = findViewById(R.id.i_image) as ImageView
        i_name = findViewById(R.id.i_name) as TextView
        skills = findViewById(R.id.skills) as TextView

        getMyData()
    }

    private fun getMyData()
    {
        val retrofitBuilder= Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(UnenrollAppInterface::class.java)

        val retrofitData=retrofitBuilder.getData()

        retrofitData.enqueue(object : Callback<UnenrollDataC?>
        {
            override fun onResponse(call: Call<UnenrollDataC?>?, response: Response<UnenrollDataC?>?)
            {

                val responseBody= response?.body()


                if (responseBody != null)
                {
                    Picasso.get()
                        .load(responseBody.image)
                        .into(c_image)

                    course_name.setText(responseBody.c_name)
                    desc.setText(responseBody.description)

                    Picasso.get()
                        .load(responseBody.faculty.image)
                        .into(f_image)

                    var str = StringBuilder()
                    str.append(responseBody.faculty.f_name)
                    str.append(responseBody.faculty.email)
                    str.append(responseBody.faculty.qualification)
                    faculty.setText(str)

                    Picasso.get()
                        .load(responseBody.faculty.institute.image)
                        .into(i_image)
                    str.clear()
                    str.append(responseBody.faculty.institute.i_name)
                    str.append(responseBody.faculty.institute.email)
                    str.append(responseBody.faculty.institute.website)
                    i_name.setText(responseBody.faculty.institute.toString())

                    str.clear()
                    for(skill in responseBody.skills)
                    {
                        str.append(skill)
                        str.append(", ")
                    }
                    skills.setText(str)
                }

            }

            override fun onFailure(call: Call<UnenrollDataC?>?, t: Throwable?) {

            }
        })



    }



}