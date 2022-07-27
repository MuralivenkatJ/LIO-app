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
import com.example.lio.Helpers.ServiceBuilder
import com.example.lio.Interfaces.Approvals
import com.example.lio.Interfaces.Institute
import com.example.lio.Models.Institute.myData_Approvals
import com.example.lio.Models.MessageResponse
import com.example.lio.R
import com.example.lio.adapters.approvalAdapter
import com.example.lio.adapters.approvalAdapter2
import com.example.lio.adapters.approvalAdapter3
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
    lateinit var navigationView : NavigationView
    lateinit  var menu: Menu

    var layoutManager: RecyclerView.LayoutManager? = null
//    var adapter: RecyclerView.Adapter<approvalAdapter.ViewHolder>? = null

    var layoutManager2: RecyclerView.LayoutManager? = null
//    var adapter2: RecyclerView.Adapter<approvalAdapter2.ViewHolder>? = null

    var layoutManager3: RecyclerView.LayoutManager? = null
//    var adapter3: RecyclerView.Adapter<approvalAdapter3.ViewHolder>? = null

    //access token
    var accessToken: String? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        //for menu bar
        binding = ApprovalsInstituteBinding.inflate(layoutInflater)
        allocateActivityTitle("Approvals")
        setTitle("Approvals")
        setContentView(binding.root)
        //for menu bar

        //updating the menu bar
        navigationView = findViewById(R.id.nav_view) as NavigationView
        menu = navigationView.menu as Menu

        menu.findItem(R.id.my_courses).setVisible(false)
        menu.findItem(R.id.wishlist).setVisible(false)
        menu.findItem(R.id.my_courses_f).setVisible(false)
        menu.findItem(R.id.login).setVisible(false)
        //updating the menu bar

        //getting the access token
        var shrdPref: SharedPreferences = getSharedPreferences("login_credentials", MODE_PRIVATE)
        accessToken  = shrdPref.getString("accessToken", "None")
        if(accessToken == "None" || accessToken == null)
        {
            var editor : SharedPreferences.Editor = shrdPref.edit()
            editor.clear()
            editor.apply()

            Toast.makeText(this, "You have to login in", Toast.LENGTH_LONG).show()
            startActivity(Intent(this, Explore::class.java))
            return
        }
        //getting the access token

        getMyData()

        layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        recycler_View.layoutManager = layoutManager

        layoutManager2 = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        recycler_View2.layoutManager = layoutManager2

        layoutManager3 = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        recycler_View3.layoutManager = layoutManager3
    }

    private fun getMyData()
    {
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("https://learn-it-online.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Approvals::class.java)


        val retrofitData = retrofitBuilder.getData("Bearer " + accessToken)

        retrofitData.enqueue(object : Callback<myData_Approvals?> {
            override fun onResponse(call: Call<myData_Approvals?>, response: Response<myData_Approvals?>) {
                val responseBody = response.body()!!

                //to check if the student is logged in
                if(responseBody.faculty_verification == null && responseBody.student_verification == null && responseBody.payment_verification == null)
                {
                    var msg = responseBody.msg
                    if(msg == "Token expired. You have to login again.")
                    {
                        Toast.makeText(this@Approvals, msg, Toast.LENGTH_LONG).show()
                        menu.performIdentifierAction(R.id.logout,0)
                        return
                    }
                    Toast.makeText(this@Approvals, msg, Toast.LENGTH_LONG).show()
                    startActivity(Intent(this@Approvals, Explore::class.java))
                    return
                }

                //Toast.makeText(this@Approvals, responseBody.toString(), Toast.LENGTH_LONG).show()
                var adapter = approvalAdapter(baseContext, responseBody.faculty_verification!!)
                recycler_View.adapter = adapter
                var adapter2 = approvalAdapter2(baseContext, responseBody.student_verification!!)
                recycler_View2.adapter = adapter2
                var adapter3 = approvalAdapter3(baseContext, responseBody.payment_verification!!)
                recycler_View3.adapter = adapter3


                //listeners
                adapter2.setOnStudentApproveListener(object: approvalAdapter2.OnStudentApproveListener
                {
                    override fun onApproveClick(position: Int)
                    {
                        var s_id = responseBody.student_verification[position]._id
                        approveStudent(s_id)
                    }

                })

                //listeners
                adapter.setOnFacultyApproveListener(object: approvalAdapter.OnFacultyApproveListener
                {
                    override fun onApproveClick(position: Int)
                    {
                        var f_id = responseBody.faculty_verification[position]._id
                        approveFaculty(f_id)
                    }

                })

                //listeners
                adapter3.setOnPaymentVerifyListener(object: approvalAdapter3.OnPaymentApproveListener
                {
                    override fun onApproveClick(position: Int)
                    {
                        var s_id = responseBody.payment_verification[position].student._id
                        var c_id = responseBody.payment_verification[position].course._id

                        verifyPayment(s_id, c_id)
                    }

                })
            }

            override fun onFailure(call: Call<myData_Approvals?>, t: Throwable) {
                Toast.makeText(this@Approvals, t.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    fun approveStudent(s_id: String)
    {
        var serviceBuilder = ServiceBuilder.buildService(Institute::class.java)
        var requestCall = serviceBuilder.approveStudent("Bearer " + accessToken, s_id)

        requestCall.enqueue(object: Callback<MessageResponse>{
            override fun onResponse(call: Call<MessageResponse>, response: Response<MessageResponse>)
            {
                if(response.body() != null)
                {
                    if(response.body()!!.msg == "This student is verified")
                    {
                        Toast.makeText(this@Approvals, response.body()!!.msg, Toast.LENGTH_LONG).show()
                        startActivity(Intent(this@Approvals, com.example.lio.Activities.Institute.Approvals::class.java))
                    }
                    else
                        Toast.makeText(this@Approvals, response.body()!!.msg, Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<MessageResponse>, t: Throwable)
            {
                Toast.makeText(this@Approvals, "Error : " + t.message, Toast.LENGTH_LONG).show()
            }

        })
    }

    fun approveFaculty(f_id: String)
    {
        var serviceBuilder = ServiceBuilder.buildService(Institute::class.java)
        var requestCall = serviceBuilder.approveFaculty("Bearer " + accessToken, f_id)

        requestCall.enqueue(object: Callback<MessageResponse>
        {
            override fun onResponse(call: Call<MessageResponse>, response: Response<MessageResponse>)
            {
                if(response.body() != null)
                {
                    if(response.body()!!.msg == "This faculty is verified")
                    {
                        Toast.makeText(this@Approvals, response.body()!!.msg, Toast.LENGTH_LONG).show()
                        startActivity(Intent(this@Approvals, com.example.lio.Activities.Institute.Approvals::class.java))
                    }
                    else
                        Toast.makeText(this@Approvals, response.body()!!.msg, Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<MessageResponse>, t: Throwable)
            {
                Toast.makeText(this@Approvals, "Error : " + t.message, Toast.LENGTH_LONG).show()
            }

        })
    }


    fun verifyPayment(s_id: String, c_id: String)
    {
        var serviceBuilder = ServiceBuilder.buildService(Institute::class.java)
        var requestCall = serviceBuilder.verifyPayment("Bearer " + accessToken, s_id, c_id)

        requestCall.enqueue(object: Callback<MessageResponse>
        {
            override fun onResponse(call: Call<MessageResponse>, response: Response<MessageResponse>)
            {
                if(response.body() != null)
                {
                    if(response.body()!!.msg == "This payment is verified")
                    {
                        Toast.makeText(this@Approvals, response.body()!!.msg, Toast.LENGTH_LONG).show()
                        startActivity(Intent(this@Approvals, com.example.lio.Activities.Institute.Approvals::class.java))
                    }
                    else
                        Toast.makeText(this@Approvals, response.body()!!.msg, Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<MessageResponse>, t: Throwable)
            {
                Toast.makeText(this@Approvals, "Error : " + t.message, Toast.LENGTH_LONG).show()
            }
        })
    }


}


