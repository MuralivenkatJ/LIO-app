package com.example.lio.Interfaces

import com.example.wishlist.wishList_MyData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface wishlist_student {

    @GET("student/wishList")
    fun getData(
        @Header("Authorization") auth: String
    ): Call<wishList_MyData>
}