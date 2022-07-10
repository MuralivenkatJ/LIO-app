package com.example.lio.Interfaces

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.Path


interface Papprovalinterface {

    @Headers("Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpX2lkIjoiNjJiZmZhNTkxMjg0N2NlODg0NWVjYTQwIiwiaWF0IjoxNjU3MzY2NzAxLCJleHAiOjE2NTc3OTg3MDF9.gwcBSNNnj25D_h1rJXRZ7FY600VKvCU6vgYFjzKrK9Q")
    @GET("verification/payment/{s_id}/{c_id}")
    fun verifyPayment (
        @Path("s_id") s: String,
        @Path("c_id") c: String
    ): Call<String>
}