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
import kotlinx.android.synthetic.main.video_player.view.*


class Student_wishList_RecyclerAdapter(val context: Context,val userList:List<wishList_Course>): RecyclerView.Adapter<Student_wishList_RecyclerAdapter.ViewHolder>()
{
    //for listener
    lateinit var enrollListener: OnEnrollClickListener
    lateinit var deleteListener: OnDeleteClickListener

    interface OnEnrollClickListener
    {
        fun onEnrollClick(position: Int)
    }
    interface OnDeleteClickListener
    {
        fun onDeleteClick(position: Int)
    }

    fun setOnEnrollClickListener(listener: OnEnrollClickListener)
    {
        enrollListener = listener
    }
    fun setOnDeleteClickListener(listener: OnDeleteClickListener)
    {
        deleteListener = listener
    }
    //for listener

    class ViewHolder(itemView: View, e_listener: OnEnrollClickListener, d_listener: OnDeleteClickListener): RecyclerView.ViewHolder(itemView)
    {
        var itemImage: ImageView
        var itemCourse: TextView
        var enroll_btn: Button
        var delete_btn: Button
        var price:TextView
        var itemRating: TextView
        var itemDesc: TextView

        init {
            itemImage=itemView.image
            itemCourse=itemView.c_title
            price=itemView.price
            enroll_btn=itemView.enroll_btn
            delete_btn=itemView.delete_btn
            itemRating=itemView.rating
            itemDesc = itemView.item_desc

            itemView.enroll_btn.setOnClickListener {
                e_listener.onEnrollClick(adapterPosition)
            }
            itemView.delete_btn.setOnClickListener {
                d_listener.onDeleteClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v= LayoutInflater.from(parent.context).inflate(R.layout.student_wishlist_card_view,parent,false)
        return ViewHolder(v, enrollListener, deleteListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemCourse.text=userList[position].c_name
        holder.itemRating.text=userList[position].rating.toString()
        holder.price.text=userList[position].price.toString()
        holder.itemDesc.text = userList[position].description
        Picasso.get()
            .load(userList[position].image)
            .into(holder.itemImage)

    }

    override fun getItemCount(): Int {
        return userList.size
    }

}