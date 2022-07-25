package com.example.lio.Models.Institute

data class Course(
    val _id: String,
    val c_name: String,
    val price: Int,
    val faculty: Faculty
)
