package com.example.lio.Interfaces

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*
import java.io.File

interface CourseInterface
{
    @Multipart
    @POST("course/upload/")
    fun uploadCourse(
        @Header("Authorization") auth: String,
        @Part("name") n: RequestBody,
        @Part("desc") d: RequestBody,
        @Part("spec") s: RequestBody,
        @Part("price") p: RequestBody,
        @Part("level") l: RequestBody,
        @Part("guided_project") g: RequestBody,
        @Part("playlist") pl: RequestBody,
        @Part("skills") sk: RequestBody,
       @Part image : MultipartBody.Part
    ): Call<String>

//    fun uploadTheCourse(
//        @Body course: UploadCourse
//    ): Response<String>

}