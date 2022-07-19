package com.example.lio.Activities.Student

//import android.support.v7.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lio.Activities.Explore
import com.example.lio.Interfaces.Student
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
                Toast.makeText(this@VideoPlayer, p1.toString(), Toast.LENGTH_LONG).show()
            }
        })


        layoutManager = LinearLayoutManager(this)
        recycler_View.layoutManager = layoutManager

        getMyData()
    }

    private fun getMyData()
    {
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Student::class.java)

        //getting accessToken
        var i = intent
        var accessToken = i.getStringExtra("accessToken")
        var c_id = i.getStringExtra("c_id")
        if(c_id == null || c_id == "")
        {
            Toast.makeText(this, "Something went wrong try again", Toast.LENGTH_LONG).show()
            startActivity(Intent(this, Explore::class.java))
            return
        }

        val retrofitData = retrofitBuilder.getVideos("Bearer " + accessToken, c_id)

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
                        YTPlayer.loadVideo(responseBody.playlistVideos[position].videoId)

                        findViewById<TextView>(R.id.title).setText(responseBody.playlistVideos[position].title)
                        findViewById<TextView>(R.id.desc).setText(responseBody.playlistVideos[position].description)

                        YTPlayer.play()
                    }

                })
            }

            override fun onFailure(call: Call<EnrolledData?>, t: Throwable)
            {
                Toast.makeText(this@VideoPlayer, "Failure " + t.message, Toast.LENGTH_LONG).show()
            }
        })

    }
}