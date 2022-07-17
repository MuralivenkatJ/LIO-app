package com.example.lio.Activities.Institute

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lio.Activities.BaseDrawer
import com.example.lio.Activities.Explore
import com.example.lio.Interfaces.Approvals
import com.example.lio.Models.Institute.myData_Approvals
import com.example.lio.R
import com.example.lio.adapters.approvalAdapter
import com.example.lio.databinding.ApprovalsInstituteBinding
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.approvals_institute.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Approvals : BaseDrawer()
{
    //for menu bar
    lateinit var binding: ApprovalsInstituteBinding

    var layoutManager: RecyclerView.LayoutManager? = null
    var adapter: RecyclerView.Adapter<approvalAdapter.ViewHolder>? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        //for menu bar
        binding = ApprovalsInstituteBinding.inflate(layoutInflater)
        allocateActivityTitle("Approvals")
        setContentView(binding.root)
        //for menu bar

        //updating the menu bar
        var navigationView = findViewById(R.id.nav_view) as NavigationView
        var menu: Menu = navigationView.menu

        menu.findItem(R.id.my_courses).setVisible(false)
        menu.findItem(R.id.wishlist).setVisible(false)
        menu.findItem(R.id.my_courses_f).setVisible(false)
        menu.findItem(R.id.login).setVisible(false)
        //updating the menu bar

        getMyData()

        layoutManager = LinearLayoutManager(this)
        recycler_View_Approvals.layoutManager = layoutManager
    }

    private fun getMyData()
    {
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("https://learn-it-online.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Approvals::class.java)


        //getting the access token
        var shrdPref: SharedPreferences = getSharedPreferences("login_credentials", MODE_PRIVATE)
        var accessToken  = shrdPref.getString("accessToken", "None")
        if(accessToken == "None" || accessToken == null)
        {
            var editor : SharedPreferences.Editor = shrdPref.edit()
            editor.clear()
            editor.apply()

            Toast.makeText(this, "You have to login in", Toast.LENGTH_LONG).show()
            startActivity(Intent(this, Explore::class.java))
            return
        }
        val retrofitData = retrofitBuilder.getData("Bearer " + accessToken)

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