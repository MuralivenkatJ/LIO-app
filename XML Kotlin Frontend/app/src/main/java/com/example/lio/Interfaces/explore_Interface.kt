package com.example.lio.Interfaces

import com.example.explore_page.explore_MyData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface explore_Interface {
    @Headers("Authorization:Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpX2lkIjoiNjJiZmZhNTkxMjg0N2NlODg0NWVjYTQwIiwiaWF0IjoxNjU3Mjk5NzU3LCJleHAiOjE2NTc3MzE3NTd9.qgJEmnb9keRra3NpO05WgAB3p_S3cxkdJPiI3wlDZzE")
    @GET("/")
    fun getData(): Call<explore_MyData>
}