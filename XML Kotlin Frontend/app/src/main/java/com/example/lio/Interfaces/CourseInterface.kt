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
    @Headers("Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJmX2lkIjoiNjJiZmZkMGYxMjg0N2NlODg0NWVjYTQ0IiwiaWF0IjoxNjU3MDE3Njc1LCJleHAiOjE2NTc0NDk2NzV9.P7-MTh4XM_esnPWQRisPO0XUlQRi1RJYw3zCrGTXg_Y")
    @POST("course/upload/")
    fun uploadCourse(
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