package com.example.lio.Activities.Faculty

//import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lio.Activities.BaseDrawer
import com.example.lio.R
import com.example.lio.databinding.UploadAssignmentBinding

class UploadAssignment : BaseDrawer()
{
    //for menu bar
    lateinit var binding: UploadAssignmentBinding

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        //for menu bar
        binding = UploadAssignmentBinding.inflate(layoutInflater)
        allocateActivityTitle("Upload Assignment")
        setContentView(binding.root)
        //for menu bar
    }
}