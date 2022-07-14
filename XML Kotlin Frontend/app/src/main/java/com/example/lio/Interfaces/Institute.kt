package com.example.lio.Interfaces

import com.example.lio.Models.Login.Login
import com.example.lio.Models.Login.LoginResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface Institute {


    @POST("institute/login")
    fun instituteLogin(
        @Body f:Login
    ): Call<LoginResponse>
   
    @Multipart
    @POST("institute/register")
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