package com.example.lio.Interfaces


import com.example.lio.Models.Login.Login
import com.example.lio.Models.Login.LoginResponse
import com.example.lio.Models.MessageResponse
import com.example.lio.Models.Student.EnrolledData
import com.example.lio.Models.Student.PaymentData
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


    @GET("/course/enrolled/{c_id}")
    fun getVideos(
        @Header("Authorization") auth: String,
        @Path("c_id") c_id: String
    ):Call<EnrolledData>

    @GET("/course/enroll/{c_id}")
    fun enroll(
        @Header("Authorization") auth: String,
        @Path("c_id") c_id: String
    ):Call<PaymentData>

    @GET("course/addToWishlist/{c_id}")
    fun addToWishlist(
        @Header("Authorization") auth: String,
        @Path("c_id") c_id: String
    ):Call<MessageResponse>

    @GET("course/removeFromWishlist/{c_id}")
    fun removeFromWishlist(
        @Header("Authorization") auth: String,
        @Path("c_id") c_id: String
    ):Call<MessageResponse>



    @Multipart
    @POST("student/payment")
    fun uploadScreenshot(
        @Header("Authorization") auth: String,
        @Part("c_id") c_id: RequestBody,
        @Part("utrid") utrid: RequestBody,
        @Part("screenshot") screenshot: MultipartBody.Part
    ):Call<MessageResponse>
}