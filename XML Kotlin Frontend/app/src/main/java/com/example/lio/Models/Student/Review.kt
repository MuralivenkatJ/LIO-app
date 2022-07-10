package com.example.lio.Models.Student

import com.example.paymentform.Models.Student

data class Review(
    val _id: String,
    val date: String,
    val desc: String,
    val rate: Int,
    val student: Student
)