package com.example.lio.adapters

import android.content.Context

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lio.R
import com.example.mycourses_lecturer.MyFaculty_Course
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.my_courses_faculty_card_layout.view.*

class MyCourses_Faculty_RecyclerAdapter(val context: Context,val userList: List<MyFaculty_Course>):RecyclerView.Adapter<MyCourses_Faculty_RecyclerAdapter.ViewHolder>()
{
    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)
    {
        var itemImage:ImageView
        var itemCourse:TextView
        var itemDescription:TextView
        var itemViews:TextView
        var itemRating:TextView
        var itemPrice:TextView
        var itemProgress:TextView
        var itemDuration:TextView
        var itemVideos:TextView
        var itemCompleted:TextView

        init {
            itemImage=itemView.image1
            itemCourse=itemView.text3
            itemDescription=itemView.text4
            itemViews=itemView.text5
            itemRating=itemView.text1
            itemPrice=itemView.text2
            itemDuration=itemView.text7
            itemVideos=itemView.text8

            itemProgress=itemView.text6
            itemCompleted=itemView.text9
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val v=LayoutInflater.from(parent.context).inflate(R.layout.my_courses_faculty_card_layout,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        holder.itemCourse.text      = userList[position].c_name
        holder.itemDescription.text = userList[position].description
        holder.itemViews.text       = userList[position].views.toString() + " views"
        holder.itemRating.text      = userList[position].rating.toString()
        holder.itemPrice.text       = userList[position].price.toString()

        holder.itemDuration.text   = userList[position].duration
        holder.itemVideos.text     = userList[position].no_of_videos.toString() + " videos"

        holder.itemProgress.text   = userList[position].inprogress.toString() + " in progress"
        holder.itemCompleted.text  = userList[position].completed.toString() + " completed"

        Picasso.get()
            .load(userList[position].image)
            .into(holder.itemImage)
    }

    override fun getItemCount(): Int {
        return userList.size
    }
}