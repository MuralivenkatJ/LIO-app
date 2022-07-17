package com.example.lio.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lio.Models.Student.PlaylistVideos
import com.example.lio.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.video_player_card.view.*

class YouTubePlayerAdapter(val context: Context, val userList1: List<PlaylistVideos>): RecyclerView.Adapter<YouTubePlayerAdapter.ViewHolder>()
{
    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)
    {
        var itemImage1: ImageView
        var itemDuration: TextView
        var itemCourse:TextView
        var itemDiscription:TextView
        init {
            itemImage1=itemView.v_thumbnail
            itemDuration=itemView.v_duration
            itemCourse=itemView.v_title
            itemDiscription=itemView.v_description

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val v= LayoutInflater.from(parent.context).inflate(R.layout.video_player_card,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        holder.itemDuration.text=userList1[position].duration
        holder.itemDiscription.text=userList1[position].description
        holder.itemCourse.text=userList1[position].title

        Picasso.get()
            .load(userList1[position].thumbnail)
            .into(holder.itemImage1)

    }

    override fun getItemCount(): Int
    {
        return userList1.size
    }
}