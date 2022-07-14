package com.example.lio.Activities.Student

//import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.RatingBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.lio.Helpers.ServiceBuilder
import com.example.lio.Interfaces.ratingInterface
import com.example.lio.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RatingStudent : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.rating_student)
    }

    fun giveRating(v: View?)
    {
        var rate : Int = Integer.parseInt(findViewById<RatingBar>(R.id.rBar).numStars.toString())
        var desc : String = findViewById<EditText>(R.id.input).text.toString()
        var c_id : String = "62c04ce0233544a84d46714e"



        var serviceBuilder = ServiceBuilder.buildService(ratingInterface::class.java)
        var requestCall = serviceBuilder.rating(rate, desc, c_id)


        requestCall.enqueue(object : Callback<String>
        {
            override fun onResponse(call: Call<String>?, response: Response<String>?) {

                if (response != null) {
                    Toast.makeText(this@RatingStudent, response.body().toString(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<String>?, t: Throwable?) {
                TODO("Not yet implemented")
            }

        })



    }


}