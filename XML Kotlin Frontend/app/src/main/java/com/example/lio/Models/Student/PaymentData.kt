package com.example.lio.Models.Student

import java.io.Serializable

data class PaymentData(
    val course: PaymentCourse?,
    val institute: PaymentInstitute?,
    val msg: String?
):Serializable
