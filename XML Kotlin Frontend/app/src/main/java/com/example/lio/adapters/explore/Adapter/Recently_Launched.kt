package com.example.explore_page.Adapter
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.example.explore_page.explore_MostViewed
import com.example.lio.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.explore_card2.view.*
import kotlinx.android.synthetic.main.explore_card2.view.c_name
import kotlinx.android.synthetic.main.payment_info_student.view.*

class Recently_Launched(val context: Context,val userList1: List<explore_MostViewed>):RecyclerView.Adapter<Recently_Launched.ViewHolder>()
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
        var itemImage2:ImageView
        var itemC_name: TextView
        var itemDescription: TextView
        var itemLevel: TextView
        var itemPrice: TextView
        var itemDuration: TextView
        var itemRating: TextView
        init {
            itemImage2=itemView.image2
            itemC_name=itemView.c_name
            itemLevel=itemView.level
            itemDescription=itemView.discription
            itemPrice=itemView.price
            itemDuration=itemView.durtion
            itemRating = itemView.c_rating

            //for listener
            itemView.setOnClickListener {
                listener.onCourseClick(adapterPosition)
            }

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v=LayoutInflater.from(parent.context).inflate(R.layout.explore_card2,parent,false)
        return ViewHolder(v, courseListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        Picasso.get()
            .load(userList1[position].image)
            .into(holder.itemImage2)

        holder.itemDuration.text=userList1[position].duration
        holder.itemLevel.text=userList1[position].level
        holder.itemDescription.text=userList1[position].description
        holder.itemC_name.text=userList1[position].c_name
        holder.itemPrice.text=userList1[position].price.toString()
        holder.itemRating.text = userList1[position].rating.toString()
    }

    override fun getItemCount(): Int {
        return userList1.size
    }
}