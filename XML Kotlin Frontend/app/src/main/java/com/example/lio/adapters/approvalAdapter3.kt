package com.example.lio.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lio.Models.Institute.PaymentVerification
import com.example.lio.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.approvalinstitute_card2.view.*

class approvalAdapter3(val context: Context, val userList: List<PaymentVerification>): RecyclerView.Adapter<approvalAdapter3.ViewHolder>()
{
    //for listener
    lateinit var approveListener: OnPaymentApproveListener

    interface OnPaymentApproveListener
    {
        fun onApproveClick(position: Int)
    }

    fun setOnPaymentVerifyListener(listener: OnPaymentApproveListener)
    {
        approveListener = listener
    }
    //for listener

    class ViewHolder(itemView: View, listener: OnPaymentApproveListener):RecyclerView.ViewHolder(itemView) {
        var itemImage: ImageView
        var itemStudent:TextView
        var itemCourse:TextView
        var itemUTR: TextView


        init {
            itemImage=itemView.image1
            itemStudent=itemView.stdent
            itemCourse=itemView.course
            itemUTR=itemView.utr

            itemView.approve_student.setOnClickListener {
                listener.onApproveClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v= LayoutInflater.from(parent.context).inflate(R.layout.approvalinstitute_card2,parent,false)
        return ViewHolder(v, approveListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemStudent.text="Name="+userList[position].student.s_name
        holder.itemCourse.text= "Course="+userList[position].course.c_name
        holder.itemUTR.text="UTR="+userList[position].utrid

        Picasso.get()
            .load(userList[position].screenshot)
            .into(holder.itemImage)
    }

    override fun getItemCount(): Int {
        return userList.size
    }
}