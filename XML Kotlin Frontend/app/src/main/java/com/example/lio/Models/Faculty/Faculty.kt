package com.example.lio.Models.Faculty

import com.example.lio.Models.Institute.Institute

data class Faculty(
    var _id: String,
    var f_name: String,
    val __v: Int,
    val email: String,
    val image: String,
    val institute: Institute,
    val phone: String,
    val qualification: String
)