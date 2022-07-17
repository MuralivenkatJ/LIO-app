package com.example.lio.Activities.Student

//import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lio.Interfaces.Student
import com.example.lio.Models.Student.PlaylistVideos
import com.example.lio.R
import com.example.lio.adapters.YouTubePlayerAdapter
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
    var adapter1: RecyclerView.Adapter<YouTubePlayerAdapter.ViewHolder>? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.video_player)

        val ytPlayer = findViewById<YouTubePlayerView>(R.id.ytPlayer)

        ytPlayer.initialize(api_key, object : YouTubePlayer.OnInitializedListener {

            override fun onInitializationSuccess(provider: YouTubePlayer.Provider?, player: YouTubePlayer?, p2: Boolean)
            {
                player?.loadVideo("HzeK7g8cD0Y")
                player?.play()
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

        val retrofitData = retrofitBuilder.getVideos("Bearer " + accessToken)

        retrofitData.enqueue(object : Callback<List<PlaylistVideos>?>
        {
            override fun onResponse(call: Call<List<PlaylistVideos>?>, response: Response<List<PlaylistVideos>?>)
            {
                val responseBody = response.body()!!
                adapter1 = YouTubePlayerAdapter(baseContext, responseBody)
                recycler_View.adapter = adapter1
            }

            override fun onFailure(call: Call<List<PlaylistVideos>?>, t: Throwable)
            {

            }
        })

    }
}