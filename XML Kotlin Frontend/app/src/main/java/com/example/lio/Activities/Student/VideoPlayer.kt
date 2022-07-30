package com.example.lio.Activities.Student

//import android.support.v7.app.AppCompatActivity
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lio.Activities.Explore
import com.example.lio.Helpers.ServiceBuilder
import com.example.lio.Interfaces.Student
import com.example.lio.Models.MessageResponse
import com.example.lio.Models.Student.EnrolledData
import com.example.lio.Models.Student.PlaylistVideos
import com.example.lio.R
import com.example.lio.adapters.YouTubePlayerAdapter
import com.google.android.material.navigation.NavigationView
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView
import kotlinx.android.synthetic.main.video_player.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class VideoPlayer : YouTubeBaseActivity()
{
    var layoutManager: RecyclerView.LayoutManager? = null
    val api_key = "AIzaSyDLVcpT3Zd4GId6R29hCXrCVaQOPfzAMSs"
//    var adapter1: RecyclerView.Adapter<YouTubePlayerAdapter.ViewHolder>? = null

    //YouTube Player
    lateinit var YTPlayer : YouTubePlayer

    //access token
    var accessToken: String? = null
    var c_id: String? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.video_player)

        val ytPlayer = findViewById<YouTubePlayerView>(R.id.ytPlayer)

        ytPlayer.initialize(api_key, object : YouTubePlayer.OnInitializedListener {

            override fun onInitializationSuccess(provider: YouTubePlayer.Provider?, player: YouTubePlayer?, p2: Boolean)
            {
                YTPlayer = player!!
//                YTPlayer.loadVideo("HzeK7g8cD0Y")
            }

            override fun onInitializationFailure(p0: YouTubePlayer.Provider?, p1: YouTubeInitializationResult?)
            {
                Toast.makeText(this@VideoPlayer, p1!!.name, Toast.LENGTH_LONG).show()
                Toast.makeText(this@VideoPlayer, p1.isUserRecoverableError().toString(), Toast.LENGTH_LONG).show()
                var errorDialog = p1!!.getErrorDialog(this@VideoPlayer, p1!!.ordinal)
                errorDialog.show()
            }
        })


        layoutManager = LinearLayoutManager(this)
        recycler_View.layoutManager = layoutManager

        //getting accessToken
        var i = intent
        accessToken = i.getStringExtra("accessToken")
        c_id = i.getStringExtra("c_id")
        if(c_id == null || c_id == "")
        {
            Toast.makeText(this, "Something went wrong try again", Toast.LENGTH_LONG).show()
            startActivity(Intent(this, Explore::class.java))
            return
        }

        getMyData()
    }


    fun watched(index: Int)
    {
        var serviceBuilder = ServiceBuilder.buildService(Student::class.java)
        var requestCall = serviceBuilder.watched("Bearer " + accessToken, c_id!!, index)

        requestCall.enqueue(object: Callback<MessageResponse>{
            override fun onResponse(call: Call<MessageResponse>, response: Response<MessageResponse>)
            {
                //Toast.makeText(this@VideoPlayer, "Success", Toast.LENGTH_LONG).show()
            }

            override fun onFailure(call: Call<MessageResponse>, t: Throwable)
            {
                //Toast.makeText(this@VideoPlayer, "Failure", Toast.LENGTH_LONG).show()
            }

        })
    }

    private fun getMyData()
    {
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Student::class.java)



        val retrofitData = retrofitBuilder.getVideos("Bearer " + accessToken, c_id!!)

        retrofitData.enqueue(object : Callback<EnrolledData?>
        {
            override fun onResponse(call: Call<EnrolledData?>, response: Response<EnrolledData?>)
            {
                val responseBody = response.body()!!

                //to check if the student is logged in
                if(responseBody.playlistVideos == null)
                {
                    var msg = responseBody.msg
                    if(msg == "Token expired. You have to login again.")
                    {
                        Toast.makeText(this@VideoPlayer, msg, Toast.LENGTH_LONG).show()
                        startActivity(Intent(this@VideoPlayer, Explore::class.java))
                        return
                    }
                    Toast.makeText(this@VideoPlayer, msg, Toast.LENGTH_LONG).show()
                    startActivity(Intent(this@VideoPlayer, Explore::class.java))
                    return
                }

                var adapter1 = YouTubePlayerAdapter(baseContext, responseBody.playlistVideos)
                recycler_View.adapter = adapter1

                //for listener
                adapter1.setOnVideoClickListener(object : YouTubePlayerAdapter.onVideoClickListener
                {
                    override fun onVideoClick(position: Int)
                    {
                        //change the image
                        var image: Drawable? = ContextCompat.getDrawable(applicationContext, R.drawable.completed)
                        findViewById<ImageView>(R.id.v_comp_icon).setImageDrawable(image)

                        YTPlayer.loadVideo(responseBody.playlistVideos[position].videoId)

                        findViewById<TextView>(R.id.title).setText(responseBody.playlistVideos[position].title)
                        findViewById<TextView>(R.id.desc).setText(responseBody.playlistVideos[position].description)

                        YTPlayer.play()

                        //calling watched
                        watched(responseBody.playlistVideos[position].index)
                    }

                })
            }

            override fun onFailure(call: Call<EnrolledData?>, t: Throwable)
            {
                Toast.makeText(this@VideoPlayer, "Error : " + t.message, Toast.LENGTH_LONG).show()
            }
        })

    }


    fun reviewPage(v: View?)
    {
        var i = Intent(this, RatingStudent::class.java)
        i.putExtra("c_id", c_id)
        i.putExtra("accessToken", accessToken)

        startActivity(i)
    }
}