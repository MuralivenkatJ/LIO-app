package com.example.lio.Activities.Institute

//import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lio.Activities.BaseDrawer
import com.example.lio.R
import com.example.lio.databinding.ApprovalsInstituteBinding

class ApprovalsInstitute : BaseDrawer()
{
    //for menu bar
    lateinit var binding: ApprovalsInstituteBinding

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        //for menu bar
        binding = ApprovalsInstituteBinding.inflate(layoutInflater)
        allocateActivityTitle("Approvals")
        setContentView(binding.root)
    }
}