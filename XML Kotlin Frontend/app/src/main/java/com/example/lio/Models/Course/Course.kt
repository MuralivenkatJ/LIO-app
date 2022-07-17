package com.example.paymentform.Models

import com.example.lio.Models.Faculty.Faculty

data class Course(
    var _id: String,
    var c_name: String,
    var faculty: Faculty,
    var price: Int
)