package com.example.lio.Activities.Faculty

//import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lio.Activities.BaseDrawer
import com.example.lio.R
import com.example.lio.databinding.UploadQuizBinding

class UploadQuiz : BaseDrawer()
{
    //for menu bar
    lateinit var binding: UploadQuizBinding

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        //for menu bar
        binding = UploadQuizBinding.inflate(layoutInflater)
        allocateActivityTitle("Upload Quiz")
        setContentView(R.layout.upload_quiz)
        //for menu bar
    }
}