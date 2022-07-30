package com.example.lio.Interfaces


import android.os.Message
import com.example.lio.Models.Login.Login
import com.example.lio.Models.Login.LoginResponse
import com.example.lio.Models.MessageResponse
import com.example.lio.Models.Student.EnrolledData
import com.example.lio.Models.Student.PaymentData
import com.example.lio.Models.Student.PlaylistVideos
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
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
    fun registerStudent(
        @Part("name") n: RequestBody,
        @Part("email") e: RequestBody,
        @Part("password") p: RequestBody,
        @Part("institute") i: RequestBody,
        @Part("phone") ph: RequestBody,
        @Part image: MultipartBody.Part
    ): Call<String>


    @GET("/course/enrolled/{c_id}")
    fun getVideos(
        @Header("Authorization") auth: String,
        @Path("c_id") c_id: String
    ):Call<EnrolledData>

    @GET("/course/watched/{c_id}/{index}")
    fun watched(
        @Header("Authorization") auth: String,
        @Path("c_id") c_id: String,
        @Path("index") index: Int
    ):Call<MessageResponse>

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

    @POST("student/review/")
    @FormUrlEncoded
    fun giveReview(
        @Header("Authorization") auth: String,
        @Field("c_id") c_id: String,
        @Field("rate") rate: Float,
        @Field("desc") desc: String
    ):Call<MessageResponse>


    @Multipart
    @POST("student/payment")
    fun uploadScreenshot(
        @HeaderMap token: HashMap<String, String>,
        @Part("c_id") c_id: RequestBody,
        @Part("utrid") utrid: RequestBody,
        @Part screenshot: MultipartBody.Part
    ):Call<MessageResponse>
}