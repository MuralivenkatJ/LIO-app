package com.example.lio.Activities.Student

//import android.support.v7.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.RatingBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.lio.Helpers.ServiceBuilder
import com.example.lio.Interfaces.Student
import com.example.lio.Interfaces.ratingInterface
import com.example.lio.Models.MessageResponse
import com.example.lio.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RatingStudent : AppCompatActivity()
{

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.rating_student)
    }

    fun giveRating(v: View?)
    {
        var rate = findViewById<RatingBar>(R.id.rBar).rating
        var desc : String = findViewById<EditText>(R.id.input).text.toString()
        var c_id : String = intent.getStringExtra("c_id")!!
        var accessToken  = intent.getStringExtra("accessToken")!!


        var serviceBuilder = ServiceBuilder.buildService(Student::class.java)
        var requestCall = serviceBuilder.giveReview("Bearer " + accessToken, c_id, rate, desc)


        requestCall.enqueue(object : Callback<MessageResponse>
        {
            override fun onResponse(call: Call<MessageResponse>?, response: Response<MessageResponse>?) {

                if (response != null) {
                    Toast.makeText(this@RatingStudent, response.body()!!.msg, Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@RatingStudent, VideoPlayer::class.java))
                }
            }

            override fun onFailure(call: Call<MessageResponse>?, t: Throwable?) {
                Toast.makeText(this@RatingStudent, "Error : " + t!!.message, Toast.LENGTH_LONG).show()
            }

        })



    }


}