package com.example.lio.Activities.Student

//import android.support.v7.app.AppCompatActivity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lio.Activities.BaseDrawer
import com.example.lio.Activities.Explore
import com.example.lio.Interfaces.wishlist_student
import com.example.lio.R
import com.example.lio.adapters.Student_wishList_RecyclerAdapter
import com.example.lio.databinding.StudentWishlistActivityMainBinding
import com.example.wishlist.wishList_MyData
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.student_wishlist_activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WishlistStudent : BaseDrawer()
{
    //for menu bar
    lateinit var binding : StudentWishlistActivityMainBinding

    var layoutManager: RecyclerView.LayoutManager? = null
    var adapter: RecyclerView.Adapter<Student_wishList_RecyclerAdapter.ViewHolder>? = null


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        //for menu bar
        binding = StudentWishlistActivityMainBinding.inflate(layoutInflater)
        allocateActivityTitle("Wishlist")
        setContentView(binding.root)
        //for menu bar

        //updating the menu bar
        var navigationView = findViewById(R.id.nav_view) as NavigationView
        var menu: Menu = navigationView.menu

        menu.findItem(R.id.my_courses_f).setVisible(false)
        menu.findItem(R.id.approvals).setVisible(false)
        menu.findItem(R.id.login).setVisible(false)
        //updating the menu bar

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

        //getting the accessToken
        var shrdPref: SharedPreferences = getSharedPreferences("login_credentials", MODE_PRIVATE)
        var accessToken = shrdPref.getString("accessToken", "None")
        if(accessToken == "None" || accessToken == null)
        {
            var editor: SharedPreferences.Editor = shrdPref.edit()
            editor.clear()
            editor.apply()

            Toast.makeText(this, "You have to log in", Toast.LENGTH_LONG).show()
            startActivity(Intent(this, Explore::class.java))
            return
        }

        val retrofitData = retrofitBuilder.getData("Bearer " + accessToken)

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