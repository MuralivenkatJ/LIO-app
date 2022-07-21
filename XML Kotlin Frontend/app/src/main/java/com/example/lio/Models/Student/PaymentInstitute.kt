package com.example.lio.Models.Student

import java.io.Serializable

data class PaymentInstitute(
    val _id: String,
    val payment_details: PaymentDetails
): Serializable
