package com.example.lio.Interfaces


import com.example.lio.Models.Login.Login
import com.example.lio.Models.Login.LoginResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*


interface Student {

    @POST("student/login")
    fun studentLogin(
        @Body f: Login
    ): Call<LoginResponse>

    //@Multipart
    @Multipart
    @POST("student/register")
    fun registerFaculty(
        @Part("name") n: RequestBody,
        @Part("email") e: RequestBody,
        @Part("password") p: RequestBody,
        @Part("qualification") q: RequestBody,
        @Part("phone") ph: RequestBody,
        @Part image: MultipartBody.Part
    ): Call<String>
}