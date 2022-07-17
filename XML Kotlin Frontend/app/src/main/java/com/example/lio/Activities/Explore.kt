package com.example.lio.Activities

//import android.support.v7.app.AppCompatActivity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.explore_page.Adapter.Guided_poject
import com.example.explore_page.Adapter.Recently_Launched
import com.example.explore_page.Adapter.RecyclerAdapter
import com.example.explore_page.explore_MyData
import com.example.lio.Interfaces.explore_Interface
import com.example.lio.R
import com.example.lio.databinding.ExploreBinding
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.explore.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Explore : BaseDrawer()
{
    //for menu bar
    lateinit var binding : ExploreBinding

    var layoutManager: RecyclerView.LayoutManager? = null
    var layoutManager1: RecyclerView.LayoutManager? = null
    var layoutManager2: RecyclerView.LayoutManager? = null
    var adapter1: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>? = null
    var adapter2: RecyclerView.Adapter<Recently_Launched.ViewHolder>? = null
    var adapter3: RecyclerView.Adapter<Guided_poject.ViewHolder>? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        //for menu bar
        binding = ExploreBinding.inflate(layoutInflater)
        allocateActivityTitle("Explore")
        setContentView(binding.root)
        //for menu bar

        //checking if logged in
        var shrdPref: SharedPreferences = getSharedPreferences("login_credentials", MODE_PRIVATE)
        var loggedInAs = shrdPref.getString("loggedInAs", "None")
        var accessToken = shrdPref.getString("accessToken", "Empty")

        Toast.makeText(this, loggedInAs, Toast.LENGTH_LONG).show()
        Toast.makeText(this, accessToken, Toast.LENGTH_LONG).show()

        //updating the menu bar
        var navigationView = findViewById(R.id.nav_view) as NavigationView
        var menu: Menu = navigationView.menu

        if(loggedInAs == "None")
        {
            menu.findItem(R.id.my_courses).setVisible(false)
            menu.findItem(R.id.wishlist).setVisible(false)
            menu.findItem(R.id.my_courses_f).setVisible(false)
            menu.findItem(R.id.approvals).setVisible(false)
            menu.findItem(R.id.profile).setVisible(false)
            menu.findItem(R.id.logout).setVisible(false)
        }
        else if(loggedInAs == "student")
        {
            menu.findItem(R.id.my_courses_f).setVisible(false)
            menu.findItem(R.id.approvals).setVisible(false)
            menu.findItem(R.id.login).setVisible(false)
        }
        else if(loggedInAs == "faculty")
        {
            menu.findItem(R.id.my_courses).setVisible(false)
            menu.findItem(R.id.wishlist).setVisible(false)
            menu.findItem(R.id.approvals).setVisible(false)
            menu.findItem(R.id.login).setVisible(false)
        }
        else if(loggedInAs == "institute")
        {
            menu.findItem(R.id.my_courses).setVisible(false)
            menu.findItem(R.id.wishlist).setVisible(false)
            menu.findItem(R.id.my_courses_f).setVisible(false)
            menu.findItem(R.id.login).setVisible(false)
        }
        //checking if logged in

        getMyData()
        layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
        recycler_View1.layoutManager = layoutManager

        layoutManager1 = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
        recycler_View2.layoutManager=layoutManager1

        layoutManager2 = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
        recycler_View3.layoutManager=layoutManager2
    }

    fun unenrolled(v: View?)
    {
        var i = Intent(this, UnenrolledCourse::class.java)
        startActivity(i)
    }

    private fun getMyData()
    {
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl(com.example.lio.Activities.explore.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(explore_Interface::class.java)

        val retrofitData = retrofitBuilder.getData()
        retrofitData.enqueue(object : Callback<explore_MyData?>
        {
            override fun onResponse(call: Call<explore_MyData?>, response: Response<explore_MyData?>)
            {
                val responseBody = response.body()!!
                adapter1 = RecyclerAdapter(baseContext, responseBody.most_viewed)
                recycler_View1.adapter = adapter1

                adapter2= Recently_Launched(baseContext,responseBody.recently_launched)
                recycler_View2.adapter=adapter2

                adapter3= Guided_poject(baseContext,responseBody.guided_project)
                recycler_View3.adapter=adapter3




            }

            override fun onFailure(call: Call<explore_MyData?>, t: Throwable) {

            }
        })
    }


}