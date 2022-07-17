package com.example.lio.Interfaces


import com.example.lio.Models.Login.Login
import com.example.lio.Models.Login.LoginResponse
import com.example.lio.Models.Student.PlaylistVideos
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*


interface Student {

    @FormUrlEncoded
    @POST("student/login")
    fun studentLogin(
        @Field("email") email: String,
        @Field("password") password: String
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


    @GET("/course/enrolled/62c04ce0233544a84d46714e")
    fun getVideos(
        @Header("Authorization") auth: String
    ):Call<List<PlaylistVideos>>
}