package com.example.lio.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lio.Models.Institute.StudentVerification
import com.example.lio.R
import kotlinx.android.synthetic.main.approvalinstitute_card.view.email
import kotlinx.android.synthetic.main.approvalinstitute_card3.view.*

class approvalAdapter2(val context: Context, val userList: List<StudentVerification>): RecyclerView.Adapter<approvalAdapter2.ViewHolder>()
{
    //for listener
    lateinit var approveListener: OnStudentApproveListener

    interface OnStudentApproveListener
    {
        fun onApproveClick(position: Int)
    }

    fun setOnStudentApproveListener(listener: OnStudentApproveListener)
    {
        approveListener = listener
    }
    //for listener

    class ViewHolder(itemView: View, listener: OnStudentApproveListener):RecyclerView.ViewHolder(itemView) {
        var itemStudent: TextView
        var itemEmail:TextView


        init {
            itemStudent=itemView.student
            itemEmail=itemView.email

            //for listener
            itemView.approve_student.setOnClickListener {
                listener.onApproveClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v= LayoutInflater.from(parent.context).inflate(R.layout.approvalinstitute_card3,parent,false)
        return ViewHolder(v, approveListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemStudent.text= "Name="+userList[position].s_name
        holder.itemEmail.text="Email="+userList[position].email
    }

    override fun getItemCount(): Int {
        return userList.size
    }
}