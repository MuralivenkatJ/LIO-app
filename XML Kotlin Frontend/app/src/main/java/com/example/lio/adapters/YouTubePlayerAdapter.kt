package com.example.lio.adapters

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.lio.Models.Student.PlaylistVideos
import com.example.lio.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.video_player_card.view.*

class YouTubePlayerAdapter(val context: Context, val userList1: List<PlaylistVideos>): RecyclerView.Adapter<YouTubePlayerAdapter.ViewHolder>()
{
    //for listener
    lateinit var videoListener: onVideoClickListener

    interface onVideoClickListener
    {
        fun onVideoClick(position: Int)
    }

    fun setOnVideoClickListener(listener: onVideoClickListener)
    {
        videoListener = listener
    }
    //for listener

    class ViewHolder(itemView: View, listener: onVideoClickListener):RecyclerView.ViewHolder(itemView)
    {
        var itemImage1: ImageView
        var itemImage2: ImageView
        var itemDuration: TextView
        var itemCourse:TextView
        var itemDiscription:TextView
        var itemIndex: TextView
        init {
            itemImage1=itemView.v_thumbnail
            itemImage2 = itemView.v_comp_icon
            itemDuration=itemView.v_duration
            itemCourse=itemView.v_title
            itemDiscription=itemView.v_description
            itemIndex = itemView.index

            //for listener
            itemView.setOnClickListener {
                listener.onVideoClick(adapterPosition)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val v= LayoutInflater.from(parent.context).inflate(R.layout.video_player_card,parent,false)
        return ViewHolder(v, videoListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        holder.itemDuration.text=userList1[position].duration
        holder.itemDiscription.text=userList1[position].description
        holder.itemCourse.text=userList1[position].title
        holder.itemIndex.text = userList1[position].index.toString()

        Picasso.get()
            .load(userList1[position].thumbnail)
            .into(holder.itemImage1)

        val image: Drawable?
        if(userList1[position].isViewed)
            image = ContextCompat.getDrawable(context.applicationContext, R.drawable.completed)
        else
            image = ContextCompat.getDrawable(context.applicationContext, R.drawable.pending)
        holder.itemImage2.setImageDrawable(image)

    }

    override fun getItemCount(): Int
    {
        return userList1.size
    }
}