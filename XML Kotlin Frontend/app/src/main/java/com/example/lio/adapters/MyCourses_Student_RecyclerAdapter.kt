package com.example.lio.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lio.Models.Student.MyCourses_Enrolled
import com.example.lio.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.my_courses_student_card_layout.view.*

//import kotlinx.android.synthetic.main.my_courses_student_card_layout.view.*
//import kotlinx.android.synthetic.main.explore.view.*


class MyCourses_Student_RecyclerAdapter(val context: Context, val userList: List<MyCourses_Enrolled>):RecyclerView.Adapter<MyCourses_Student_RecyclerAdapter.ViewHolder>() {
    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
                var itemImage:ImageView
                var itemCourse:TextView
                var itemFaculty:TextView
                var itemViews:TextView
                var itemRating:TextView

                init {
                    itemImage=itemView.item_image
                    itemCourse=itemView.item_course
                    itemFaculty=itemView.item_faculty
                    itemViews=itemView.item_views
                    itemRating=itemView.item_rating
                }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v=LayoutInflater.from(parent.context).inflate(R.layout.my_courses_student_card_layout,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemCourse.text = userList[position].myCoursesCourse.c_name
        holder.itemFaculty.text= userList[position].myCoursesCourse.myCoursesFaculty.f_name
        holder.itemViews.text= userList[position].myCoursesCourse.views.toString()
        holder.itemRating.text= userList[position].myCoursesCourse.rating.toString()

        Picasso.get()
            .load(userList[position].myCoursesCourse.image)
            .into(holder.itemImage)
    }

    override fun getItemCount(): Int {
        return userList.size
    }
}