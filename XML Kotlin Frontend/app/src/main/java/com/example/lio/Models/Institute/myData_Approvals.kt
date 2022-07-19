package com.example.lio.Models.Institute

data class myData_Approvals(
    val faculty_verification: List<FacultyVerification_approvals>?,
    val payment_verification: List<Any>?,
    val student_verification: List<Any>?,
    val msg: String?
)