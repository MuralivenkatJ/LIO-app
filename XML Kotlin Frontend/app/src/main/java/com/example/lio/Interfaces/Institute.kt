package com.example.lio.Interfaces

import com.example.lio.Models.Login.Login
import com.example.lio.Models.Login.LoginResponse
import com.example.lio.Models.MessageResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface Institute {


    @FormUrlEncoded
    @POST("institute/login")
    fun instituteLogin(
        @Field("email") email: String,
        @Field("password") password: String
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

    @GET("verification/student/{s_id}")
    fun approveStudent(
        @Header("Authorization") auth: String,
        @Path("s_id") s_id: String
    ):Call<MessageResponse>

    @GET("verification/faculty/{f_id}")
    fun approveFaculty(
        @Header("Authorization") auth: String,
        @Path("f_id") f_id: String
    ):Call<MessageResponse>

    @GET("verification/payment/{s_id}/{c_id}")
    fun verifyPayment(
        @Header("Authorization") auth: String,
        @Path("s_id") s_id: String,
        @Path("c_id") c_id: String
    ):Call<MessageResponse>
}