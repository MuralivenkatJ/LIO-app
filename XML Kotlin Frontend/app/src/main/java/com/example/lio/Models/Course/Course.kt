package com.example.paymentform.Models

data class Course(
    var _id: String,
    var c_name: String,
    var faculty: Faculty,
    var price: Int
)