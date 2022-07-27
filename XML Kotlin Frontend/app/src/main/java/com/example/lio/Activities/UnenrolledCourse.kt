package com.example.lio.Activities

//import android.support.v7.app.AppCompatActivity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import com.example.lio.R

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.lio.Activities.Student.MyCoursesStudent
import com.example.lio.Activities.Student.PaymentInfoStudent
import com.example.lio.Activities.Student.StudentLogin
import com.example.lio.Activities.Student.WishlistStudent
import com.example.lio.Helpers.ServiceBuilder
import com.example.lio.Interfaces.Student
import com.example.lio.Interfaces.UnenrollAppInterface
import com.example.lio.Models.MessageResponse
import com.example.lio.Models.Student.PaymentData
import com.example.lio.Models.UnenrollDataC
import com.squareup.picasso.Picasso
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


    var accessToken: String? = null
    var c_id: String = ""

    lateinit var enrollBtn: Button
    lateinit var wishlistBtn: Button


    override fun onCreate(savedInstanceState: Bundle?)
    {
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

        enrollBtn = findViewById(R.id.delete_btn) as Button
        wishlistBtn = findViewById(R.id.add_to_wishlist) as Button

        //getting the accessToken
        var shrdPref: SharedPreferences = getSharedPreferences("login_credentials", MODE_PRIVATE)
        var loggedInAs = shrdPref.getString("loggedInAs", "None")
        accessToken = shrdPref.getString("accessToken", "None")
        if(loggedInAs != "student" ||accessToken == "None" || accessToken == null)
        {
            enrollBtn.setOnClickListener {
                Toast.makeText(this@UnenrolledCourse, "You have to login as student", Toast.LENGTH_LONG).show()
                if(loggedInAs == "None")
                    startActivity(Intent(this, StudentLogin::class.java))
            }
            wishlistBtn.setOnClickListener {
                Toast.makeText(this@UnenrolledCourse, "You have to login as student", Toast.LENGTH_LONG).show()
                if(loggedInAs == "None")
                    startActivity(Intent(this, StudentLogin::class.java))
            }
        }
        else
        {
            enrollBtn.setOnClickListener{
                enroll(it)
            }
            wishlistBtn.setOnClickListener{
                addToWishlist(it)
            }
        }
    }

    private fun getMyData()
    {
        val retrofitBuilder= Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(UnenrollAppInterface::class.java)

        //getting the course id
        var i = intent
        c_id = i.getStringExtra("c_id")!!
        if(c_id == null || c_id == "")
        {
            Toast.makeText(this, "Something went wrong try again", Toast.LENGTH_LONG).show()
            startActivity(Intent(this, Explore::class.java))
            return
        }

        val retrofitData=retrofitBuilder.getData(c_id)

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
                    str.append(responseBody.faculty.f_name + "\n")
                    str.append(responseBody.faculty.email + "\n")
                    str.append(responseBody.faculty.qualification + "\n")
                    faculty.setText(str)

                    Picasso.get()
                        .load(responseBody.faculty.institute.image)
                        .into(i_image)
                    str.clear()
                    str.append(responseBody.faculty.institute.i_name + "\n")
                    str.append(responseBody.faculty.institute.email + "\n")
                    str.append(responseBody.faculty.institute.website + "\n")
                    i_name.setText(str)

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
                Toast.makeText(this@UnenrolledCourse, "Error : " + t!!.message, Toast.LENGTH_LONG).show()
            }
        })



    }


    fun enroll(v: View?)
    {
        val serviceBuilder = ServiceBuilder.buildService(Student::class.java)
        val requestCall = serviceBuilder.enroll("Bearer " + accessToken, c_id)

        requestCall.enqueue(object: Callback<PaymentData>
        {
            override fun onResponse(call: Call<PaymentData>, response: Response<PaymentData>)
            {
                var responseBody = response.body()

                if (responseBody != null)
                {
                    //if the course is free it will directly enroll
                    if(responseBody.msg == "This course has been enrolled" ||  responseBody.msg == "You are already enrolled for this course")
                    {
                        Toast.makeText(this@UnenrolledCourse, responseBody.msg, Toast.LENGTH_LONG).show()
                        startActivity(Intent(this@UnenrolledCourse, MyCoursesStudent::class.java))
                        return
                    }
                    else{

                        if(responseBody.course != null && responseBody.institute != null)
                        {
                            var i = Intent(this@UnenrolledCourse, PaymentInfoStudent::class.java)
                            i.putExtra("c_name", responseBody.course!!.c_name)
                            i.putExtra("price", responseBody.course!!.price.toString())
                            i.putExtra("ac_no", responseBody.institute!!.payment_details.ac_no)
                            i.putExtra("ifsc", responseBody.institute!!.payment_details.ifsc)
                            i.putExtra("ac_name", responseBody.institute!!.payment_details.ac_name)
                            i.putExtra("c_id", responseBody.course!!._id)


                            startActivity(i)
                        }
                        else
                            Toast.makeText(this@UnenrolledCourse, "Something went wrong", Toast.LENGTH_LONG).show()
                    }
                }
            }

            override fun onFailure(call: Call<PaymentData>, t: Throwable)
            {
                Toast.makeText(this@UnenrolledCourse, "Error : " + t.message, Toast.LENGTH_LONG).show()
            }

        })
    }

    fun addToWishlist(v: View?)
    {
        var serviceBuilder = ServiceBuilder.buildService(Student::class.java)
        var requestCall = serviceBuilder.addToWishlist("Bearer " + accessToken, c_id)

        requestCall.enqueue(object: Callback<MessageResponse>
        {
            override fun onResponse(call: Call<MessageResponse>, response: Response<MessageResponse>)
            {
                Toast.makeText(this@UnenrolledCourse, response.body()!!.msg, Toast.LENGTH_LONG).show()
                if(response.body()!!.msg == "This course has been added to the wishlist")
                {
                    startActivity(Intent(this@UnenrolledCourse, WishlistStudent::class.java))
                    return
                }
            }

            override fun onFailure(call: Call<MessageResponse>, t: Throwable)
            {
                Toast.makeText(this@UnenrolledCourse, "Error : " + t.message, Toast.LENGTH_LONG).show()
            }

        })
    }

}