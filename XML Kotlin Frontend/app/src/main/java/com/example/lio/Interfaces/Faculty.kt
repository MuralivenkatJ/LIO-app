package com.example.lio.Interfaces

import com.example.lio.Models.Institute.GetInstitutes
import com.example.lio.Models.Login.Login
import com.example.lio.Models.Login.LoginResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*


interface Faculty {

 @GET("getInstitutes")
    fun getInstitutes(): Call<List<GetInstitutes>>

    //@Multipart
    @Multipart
    @POST("faculty/register")
    fun registerFaculty(
        @Part("name") n:RequestBody,
        @Part("email") e:RequestBody,
        @Part("password") p:RequestBody,
        @Part("qualification") q:RequestBody,
        @Part("phone") ph:RequestBody,
        @Part("institute") i:RequestBody,
        @Part image: MultipartBody.Part
    ): Call<String>

    @POST("faculty/login")
    fun login(
        @Body f: Login
    ): Call<LoginResponse>
}

