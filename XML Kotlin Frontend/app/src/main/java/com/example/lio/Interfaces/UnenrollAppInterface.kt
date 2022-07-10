package com.example.lio.Interfaces

import com.example.lio.Models.UnenrollDataC
import retrofit2.Call
import retrofit2.http.GET

interface UnenrollAppInterface {

    @GET("course/unenrolled/62c04ce0233544a84d46714e/")
    fun getData() : Call<UnenrollDataC>
}