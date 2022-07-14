package com.example.explore_page.Adapter
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

import com.example.explore_page.explore_MostViewed
import com.example.lio.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.explore_card2.view.*

class Recently_Launched(val context: Context,val userList1: List<explore_MostViewed>):RecyclerView.Adapter<Recently_Launched.ViewHolder>() {
    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        var itemImage2:ImageView
        init {
            itemImage2=itemView.image2

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v=LayoutInflater.from(parent.context).inflate(R.layout.explore_card2,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Picasso.get()
            .load(userList1[position].image)
            .into(holder.itemImage2)

    }

    override fun getItemCount(): Int {
        return userList1.size
    }
}