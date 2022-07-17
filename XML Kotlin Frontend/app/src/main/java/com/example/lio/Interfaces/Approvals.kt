package com.example.lio.Interfaces
import com.example.lio.Models.Institute.myData_Approvals
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface Approvals {

    @GET("institute/approvals")
    fun getData(
        @Header("Authorization") auth: String
    ):Call<myData_Approvals>
}