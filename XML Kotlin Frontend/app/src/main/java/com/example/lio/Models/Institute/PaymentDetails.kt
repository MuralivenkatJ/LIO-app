package com.example.paymentform.Models

data class PaymentDetails(
    var _id: String,
    var course: Course,
    var date: String,
    var screenshot: String,
    var student: Student,
    var utrid: String
)