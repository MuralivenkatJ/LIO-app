package com.example.lio.Activities.Institute

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.example.lio.R
import com.squareup.picasso.Picasso

import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.example.lio.Helpers.ServiceBuilder
import com.example.lio.Interfaces.Papprovalinterface
import com.example.paymentform.Models.PaymentDetails
import com.example.paymentform.Models.Student
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

const val BASE_URL="https://learn-it-online.herokuapp.com/"


class PaymentVerification : androidx.appcompat.app.AppCompatActivity()
{

    lateinit var data : PaymentDetails

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.payment_verification)



//        var s= Student("62bfcbeb893656b6d7987b76","Manjush V G")
//        var f= Faculty("62bffd0f12847ce8845eca44","Chakrapani D S")
//        var c= Course("62c83fa18fc149e9c9f1fd4f","Node js ",f,899)
//        data= PaymentDetails("62c96b431ae3dd5ae718545e",c,"2022-07-09T11:49:23.603Z","https://learn-it-online.herokuapp.com/payment_screenshots/screenshot_1657367362918.jpg",s,"123456789120")
//
//
//        //elements
//
//
//        var f_name=findViewById<TextView>(R.id.f_name)
//        var c_name=findViewById<TextView>(R.id.c_name)
//        var s_name=findViewById<TextView>(R.id.s_name)
//        var utr=findViewById<TextView>(R.id.utr)
//        var price=findViewById<TextView>(R.id.price)
//        var image=findViewById(R.id.imageView) as ImageView
//        var date=findViewById<TextView>(R.id.date)
//
//        c_name.text=data.course.c_name
//        f_name.text=data.course.faculty.f_name
//        s_name.text=data.student.s_name
//        utr.text=data.utrid
//        price.text=data.course.price.toString()
//        date.text=data.date
//
//        Picasso.with(this)
//            .load(data.screenshot)
//            .into(image)


    }

    fun approve(v: View?)
    {
        verifyPayment(data.student._id, data.course._id)
    }


    fun verifyPayment(s_id: String, c_id: String)
    {
        val serviceBuilder = ServiceBuilder.buildService(Papprovalinterface::class.java)
        val requestCall = serviceBuilder.verifyPayment(s_id, c_id)

        requestCall.enqueue(object : Callback<String?>
        {
            override fun onResponse(call: Call<String?>?, response: Response<String?>?)
            {

                val responseBody= response?.body()

                Toast.makeText(this@PaymentVerification,"Verified",Toast.LENGTH_SHORT).show()



            }

            override fun onFailure(call: Call<String?>, t: Throwable) {

                Toast.makeText(this@PaymentVerification,"----- ",Toast.LENGTH_SHORT).show()
            }


        })
    }


}