package com.example.lio.Interfaces

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface Institute {

   
    @Multipart
    @POST("Institute/register")
    fun registerFaculty(
        @Part("name") n: RequestBody,
        @Part("email") e: RequestBody,
        @Part("website") w: RequestBody,
        @Part("account_name") ah: RequestBody,
        @Part("account_no") an: RequestBody,
        @Part("ifsc") i: RequestBody,
        @Part("password") p: RequestBody,
        @Part image: MultipartBody.Part
    ): Call<String>
}