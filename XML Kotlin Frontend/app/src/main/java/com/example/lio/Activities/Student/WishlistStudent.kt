package com.example.lio.Activities.Student

//import android.support.v7.app.AppCompatActivity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lio.Activities.BaseDrawer
import com.example.lio.Activities.Explore
import com.example.lio.Helpers.ServiceBuilder
import com.example.lio.Interfaces.Student
import com.example.lio.Interfaces.wishlist_student
import com.example.lio.Models.MessageResponse
import com.example.lio.Models.Student.PaymentData
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
    lateinit var navigationView : NavigationView
    lateinit  var menu: Menu

    var accessToken: String? = null
    var c_id: String = ""

    var layoutManager: RecyclerView.LayoutManager? = null
    //var adapter: RecyclerView.Adapter<Student_wishList_RecyclerAdapter.ViewHolder>? = null


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        //for menu bar
        binding = StudentWishlistActivityMainBinding.inflate(layoutInflater)
        allocateActivityTitle("Wishlist")
        setTitle("Wishlist")
        setContentView(binding.root)
        //for menu bar

        //updating the menu bar
        navigationView = findViewById(R.id.nav_view) as NavigationView
        menu = navigationView.menu as Menu

        menu.findItem(R.id.my_courses_f).setVisible(false)
        menu.findItem(R.id.approvals).setVisible(false)
        menu.findItem(R.id.login).setVisible(false)
        //updating the menu bar

        getMyData()
        layoutManager = LinearLayoutManager(this)
        wishlist_recyclerView.layoutManager = layoutManager



        //getting the accessToken
        var shrdPref: SharedPreferences = getSharedPreferences("login_credentials", MODE_PRIVATE)
        var loggedInAs = shrdPref.getString("loggedInAs", "None")
        accessToken = shrdPref.getString("accessToken", "None")

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

        retrofitData.enqueue(object : Callback<wishList_MyData?>
        {
            override fun onResponse(call: Call<wishList_MyData?>, response: Response<wishList_MyData?>)
            {
                val responseBody=response.body()!!

                //to check if the student is logged in
                if(responseBody.wishlist == null)
                {
                    var msg = responseBody.msg
                    if(msg == "Token expired. You have to login again.")
                    {
                        Toast.makeText(this@WishlistStudent, msg, Toast.LENGTH_LONG).show()
                        menu.performIdentifierAction(R.id.logout,0)
                        return
                    }
                    Toast.makeText(this@WishlistStudent, msg, Toast.LENGTH_LONG).show()
                    startActivity(Intent(this@WishlistStudent, Explore::class.java))
                    return
                }

                var adapter = Student_wishList_RecyclerAdapter(baseContext,responseBody.wishlist)
                wishlist_recyclerView.adapter = adapter

                adapter.setOnEnrollClickListener(object: Student_wishList_RecyclerAdapter.OnEnrollClickListener{
                    override fun onEnrollClick(position: Int)
                    {
                        var c_id = response.body()!!.wishlist!![position]._id
                        enroll(c_id)
                    }

                })

                adapter.setOnDeleteClickListener(object: Student_wishList_RecyclerAdapter.OnDeleteClickListener{
                    override fun onDeleteClick(position: Int)
                    {
                        var c_id = response.body()!!.wishlist!![position]._id
                        removeFromWishlist(c_id)
                    }

                })
            }

            override fun onFailure(call: Call<wishList_MyData?>, t: Throwable)
            {

            }
        })
    }

    fun enroll(c_id: String)
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
                    if(responseBody.msg == "This course has been enrolled" || responseBody.msg == "You are already enrolled for this course")
                    {
                        Toast.makeText(this@WishlistStudent, responseBody.msg, Toast.LENGTH_LONG).show()
                        startActivity(Intent(this@WishlistStudent, MyCoursesStudent::class.java))
                        return
                    }
                    else{
                        if(responseBody.course != null && responseBody.institute != null)
                        {
                            var i = Intent(this@WishlistStudent, PaymentInfoStudent::class.java)
                            i.putExtra("c_name", responseBody.course!!.c_name)
                            i.putExtra("price", responseBody.course!!.price.toString())
                            i.putExtra("ac_no", responseBody.institute!!.payment_details.ac_no)
                            i.putExtra("ifsc", responseBody.institute!!.payment_details.ifsc)
                            i.putExtra("ac_name", responseBody.institute!!.payment_details.ac_name)
                            i.putExtra("c_id", responseBody.course!!._id)


                            startActivity(i)
                        }
                        else
                            Toast.makeText(this@WishlistStudent, responseBody.msg, Toast.LENGTH_LONG).show()
                    }
                }
            }

            override fun onFailure(call: Call<PaymentData>, t: Throwable)
            {
                Toast.makeText(this@WishlistStudent, "Error : " + t.message, Toast.LENGTH_LONG).show()
            }

        })
    }

    fun removeFromWishlist(c_id: String)
    {
        var serviceBuilder = ServiceBuilder.buildService(Student::class.java)
        var requestCall = serviceBuilder.removeFromWishlist("Bearer " + accessToken, c_id)

        requestCall.enqueue(object: Callback<MessageResponse>{
            override fun onResponse(call: Call<MessageResponse>, response: Response<MessageResponse>)
            {
                Toast.makeText(this@WishlistStudent, response.body()!!.msg, Toast.LENGTH_LONG).show()
                startActivity(Intent(this@WishlistStudent, WishlistStudent::class.java))
            }

            override fun onFailure(call: Call<MessageResponse>, t: Throwable)
            {
                Toast.makeText(this@WishlistStudent, "Error : " +  t.message, Toast.LENGTH_LONG).show()
            }

        })
    }


}