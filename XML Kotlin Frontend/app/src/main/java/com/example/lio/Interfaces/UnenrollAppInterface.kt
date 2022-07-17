package com.example.lio.Interfaces

import com.example.lio.Models.Course.EnrollResponse
import com.example.lio.Models.UnenrollDataC
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface UnenrollAppInterface {

    @GET("course/unenrolled/{c_id}")
    fun getData(
        @Path("c_id") c_id: String
    ) : Call<UnenrollDataC>

    @GET("course/enroll/{c_id}")
    fun enroll(
        @Header("Authorization") auth: String,
        @Path("c_id") c_id: String
    ): Call<EnrollResponse>
}