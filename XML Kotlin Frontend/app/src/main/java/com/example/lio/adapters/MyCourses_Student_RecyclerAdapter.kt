package com.example.lio.adapters

import android.animation.ObjectAnimator
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lio.Models.Student.MyCourses_Enrolled
import com.example.lio.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.my_courses_student_card_layout.view.*

//import kotlinx.android.synthetic.main.my_courses_student_card_layout.view.*
//import kotlinx.android.synthetic.main.explore.view.*


class MyCourses_Student_RecyclerAdapter(val context: Context, val userList: List<MyCourses_Enrolled>):RecyclerView.Adapter<MyCourses_Student_RecyclerAdapter.ViewHolder>()
{
    //for listener
    lateinit var courseListener: OnCourseClickListener

    interface OnCourseClickListener
    {
        fun onCourseClick(position: Int)
    }

    fun setOnCourseClickListener(listener: OnCourseClickListener)
    {
        courseListener = listener
    }
    //for listener


    class ViewHolder(itemView: View, listener: OnCourseClickListener):RecyclerView.ViewHolder(itemView)
    {
        var itemImage:ImageView
        var itemCourse:TextView
        var itemFaculty:TextView
        var itemViews:TextView
        var itemRating:TextView
        var progressBar: ProgressBar

        init {
            itemImage=itemView.item_image
            itemCourse=itemView.item_desc
            itemFaculty=itemView.item_faculty
            itemViews=itemView.item_views
            itemRating=itemView.item_rating
            progressBar = itemView.progressBar

            //for listener
            itemView.setOnClickListener {
                listener.onCourseClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val v=LayoutInflater.from(parent.context).inflate(R.layout.my_courses_student_card_layout,parent,false)
        return ViewHolder(v, courseListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        holder.itemCourse.text = userList[position].course.c_name
        holder.itemFaculty.text= userList[position].course.faculty.f_name
        holder.itemViews.text= userList[position].course.views.toString() + " views"
        holder.itemRating.text= userList[position].course.rating.toString()

        Picasso.get()
            .load(userList[position].course.image)
            .into(holder.itemImage)

        holder.progressBar.max = userList[position].course.no_of_videos
        ObjectAnimator.ofInt(holder.progressBar, "progress", userList[position].watched.size)
            .start()
    }

    override fun getItemCount(): Int
    {
        return userList.size
    }
}