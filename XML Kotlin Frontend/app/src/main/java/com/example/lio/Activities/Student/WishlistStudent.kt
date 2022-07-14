package com.example.lio.Activities.Student

//import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lio.Interfaces.wishlist_student
import com.example.lio.R
import com.example.lio.adapters.Student_wishList_RecyclerAdapter
import com.example.wishlist.wishList_MyData
import kotlinx.android.synthetic.main.student_wishlist_activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WishlistStudent : AppCompatActivity() {

    var layoutManager: RecyclerView.LayoutManager? = null
    var adapter: RecyclerView.Adapter<Student_wishList_RecyclerAdapter.ViewHolder>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.student_wishlist_activity_main)

        getMyData()
        layoutManager = LinearLayoutManager(this)
        wishlist_recyclerView.layoutManager = layoutManager

    }

    private fun getMyData() {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(wishlist_student::class.java)

        val retrofitData = retrofitBuilder.getData()

        retrofitData.enqueue(object : Callback<wishList_MyData?> {
            override fun onResponse(
                call: Call<wishList_MyData?>,
                response: Response<wishList_MyData?>
            ) {
                val responseBody=response.body()!!
                adapter = Student_wishList_RecyclerAdapter(baseContext,responseBody.wishlist)
                wishlist_recyclerView.adapter = adapter
            }

            override fun onFailure(call: Call<wishList_MyData?>, t: Throwable) {

            }
        })
    }

}