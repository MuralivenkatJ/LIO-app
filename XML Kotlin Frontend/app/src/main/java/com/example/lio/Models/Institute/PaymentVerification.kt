package com.example.lio.Models.Institute

data class PaymentVerification(
    val _id: String,
    val course: Course,
    val date: String,
    val screenshot: String,
    val student: Student,
    val utrid: String
)