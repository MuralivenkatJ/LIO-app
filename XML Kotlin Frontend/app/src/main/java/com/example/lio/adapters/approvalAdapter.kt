package com.example.lio.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lio.Models.Institute.FacultyVerification_approvals
import com.example.lio.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.approvalinstitute_card.view.*

class approvalAdapter(val context: Context,val userList: List<FacultyVerification_approvals>):RecyclerView.Adapter<approvalAdapter.ViewHolder>()
{
    //for listener
    lateinit var approveListener: OnFacultyApproveListener

    interface OnFacultyApproveListener
    {
        fun onApproveClick(position: Int)
    }

    fun setOnFacultyApproveListener(listener: OnFacultyApproveListener)
    {
        approveListener = listener
    }
    //for listener


    class ViewHolder(itemView: View, listener: OnFacultyApproveListener):RecyclerView.ViewHolder(itemView) {
        var itemImage:ImageView
        var itemQualification:TextView
        var itemFaculty:TextView
        var itemEmail:TextView


        init {
            itemImage=itemView.image
            itemQualification=itemView.qualification
            itemFaculty=itemView.faculty
            itemEmail=itemView.email

            itemView.approve_student.setOnClickListener {
                listener.onApproveClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v=LayoutInflater.from(parent.context).inflate(R.layout.approvalinstitute_card,parent,false)
        return ViewHolder(v, approveListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemFaculty.text= userList[position].f_name
        holder.itemEmail.text=userList[position].email
        holder.itemQualification.text= userList[position].qualification

        Picasso.get()
            .load(userList[position].image)
            .into(holder.itemImage)
    }

    override fun getItemCount(): Int {
        return userList.size
    }
}