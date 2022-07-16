package com.example.lio.Activities.Institute

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lio.Interfaces.Approvals
import com.example.lio.Models.Institute.myData_Approvals
import com.example.lio.R
import com.example.lio.adapters.approvalAdapter
import kotlinx.android.synthetic.main.approvals_institute.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Approvals : AppCompatActivity() {
    var layoutManager: RecyclerView.LayoutManager? = null
    var adapter: RecyclerView.Adapter<approvalAdapter.ViewHolder>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.approvals_institute)

        getMyData()

        layoutManager = LinearLayoutManager(this)
        recycler_View_Approvals.layoutManager = layoutManager
    }

    private fun getMyData() {
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("https://learn-it-online.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Approvals::class.java)

        val retrofitData = retrofitBuilder.getData()
        retrofitData.enqueue(object : Callback<myData_Approvals?> {
            override fun onResponse(call: Call<myData_Approvals?>, response: Response<myData_Approvals?>) {
                val responseBody = response.body()!!
                adapter = approvalAdapter(baseContext, responseBody.faculty_verification)
                recycler_View_Approvals.adapter = adapter
            }

            override fun onFailure(call: Call<myData_Approvals?>, t: Throwable) {

            }
        })
    }
}