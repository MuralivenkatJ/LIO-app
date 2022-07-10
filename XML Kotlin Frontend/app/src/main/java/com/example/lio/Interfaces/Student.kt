package com.example.lio.Interfaces

import com.example.register.Models.GetInstitutes
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*


interface Student {

   @GET("getInstitutes")
    fun getInstitutes(): Call<List<GetInstitutes>>

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