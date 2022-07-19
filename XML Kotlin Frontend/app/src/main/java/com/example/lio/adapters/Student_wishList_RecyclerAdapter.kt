package com.example.lio.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lio.R
import com.example.wishlist.wishList_Course
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.student_wishlist_card_view.view.*

class Student_wishList_RecyclerAdapter(val context: Context,val userList:List<wishList_Course>): RecyclerView.Adapter<Student_wishList_RecyclerAdapter.ViewHolder>() {

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var itemImage: ImageView
        var itemCourse: TextView
        var button1: Button
        var button2:Button
        var price:TextView
        var itemRating: TextView

        init {
            itemImage=itemView.image
            itemCourse=itemView.item_course
            price=itemView.price
            button1=itemView.button1
            button2=itemView.enroll
            itemRating=itemView.rating
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v= LayoutInflater.from(parent.context).inflate(R.layout.student_wishlist_card_view,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemCourse.text=userList[position].c_name
        holder.itemRating.text=userList[position].rating.toString()
        holder.price.text=userList[position].price.toString()
        Picasso.get()
            .load(userList[position].image)
            .into(holder.itemImage)

    }

    override fun getItemCount(): Int {
        return userList.size
    }
}