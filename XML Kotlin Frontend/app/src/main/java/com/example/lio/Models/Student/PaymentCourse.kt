package com.example.lio.Models.Student

data class PaymentCourse(
    val _id: String,
    val c_name: String,
    val faculty: PaymentFaculty,
    val price: Int
)
