package com.example.lio.Activities.Faculty

//import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lio.Activities.BaseDrawer
import com.example.lio.R
import com.example.lio.databinding.AsgnAndQuizFacultyBinding

class AsgnAndQuizFaculty : BaseDrawer()
{
    //for menu bar
    lateinit var binding: AsgnAndQuizFacultyBinding

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        //for menu bar
        binding = AsgnAndQuizFacultyBinding.inflate(layoutInflater)
        allocateActivityTitle("Assignment and Quiz")
        setContentView(binding.root)
        //for menu bar
    }
}