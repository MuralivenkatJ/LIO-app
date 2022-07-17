package com.example.lio.Models

import com.example.lio.Models.Student.Review
import com.example.lio.Models.Faculty.Faculty

data class UnenrollDataC(

    val __v: Int,
    val _id: String,
    val c_name: String,
    val date: String,
    val description: String,
    val duration: String,
    val faculty: Faculty,
    val guided_project: Boolean,
    val image: String,
    val level: String,
    val no_of_videos: Int,
    val price: Int,
    val rating: Int,
    val reviews: List<Review>,
    val skills: List<String>,
    val specialization: String,
    val views: Int
)
