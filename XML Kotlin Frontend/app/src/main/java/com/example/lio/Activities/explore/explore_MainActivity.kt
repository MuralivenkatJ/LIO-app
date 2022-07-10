package com.example.lio.Activities.explore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.explore_page.Adapter.Guided_poject
import com.example.explore_page.Adapter.Recently_Launched
import com.example.explore_page.Adapter.RecyclerAdapter
import com.example.explore_page.explore_MostViewed
import com.example.explore_page.explore_MyData
import com.example.lio.Interfaces.explore_Interface
import com.example.lio.R
import kotlinx.android.synthetic.main.explore.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL="https://learn-it-online.herokuapp.com/"
class explore_MainActivity : AppCompatActivity() {
    var layoutManager: RecyclerView.LayoutManager? = null
    var layoutManager1: RecyclerView.LayoutManager? = null
    var layoutManager2: RecyclerView.LayoutManager? = null
    var adapter1: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>? = null
    var adapter2: RecyclerView.Adapter<Recently_Launched.ViewHolder>? = null
    var adapter3: RecyclerView.Adapter<Guided_poject.ViewHolder>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.explore)

        getMyData()
        layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        recycler_View1.layoutManager = layoutManager

        layoutManager1 = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        recycler_View2.layoutManager=layoutManager1

        layoutManager2 = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        recycler_View3.layoutManager=layoutManager2
    }

    private fun getMyData() {
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(explore_Interface::class.java)

        val retrofitData = retrofitBuilder.getData()
        retrofitData.enqueue(object : Callback<explore_MyData?> {
            override fun onResponse(call: Call<explore_MyData?>, response: Response<explore_MyData?>) {
                val responseBody = response.body()!!
                adapter1 = RecyclerAdapter(baseContext, responseBody.most_viewed)
                recycler_View1.adapter = adapter1

                adapter2=Recently_Launched(baseContext,responseBody.recently_launched)
                recycler_View2.adapter=adapter2

                adapter3=Guided_poject(baseContext,responseBody.guided_project)
                recycler_View3.adapter=adapter3




            }

            override fun onFailure(call: Call<explore_MyData?>, t: Throwable) {

            }
        })
    }
}