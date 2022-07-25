package com.example.lio.Models.Institute

data class myData_Approvals(
    val faculty_verification: List<FacultyVerification_approvals>?,
    val payment_verification: List<PaymentVerification>?,
    val student_verification: List<StudentVerification>?,
    val msg: String?
)