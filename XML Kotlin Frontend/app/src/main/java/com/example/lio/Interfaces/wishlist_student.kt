package com.example.lio.Interfaces

import com.example.wishlist.wishList_MyData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface wishlist_student {
    @Headers("Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzX2lkIjoiNjJiZmRlY2M1NDgxYjNhNTk2MWVjMzY4IiwiaWF0IjoxNjU3MTU0Nzg1LCJleHAiOjE2NTc1ODY3ODV9.JhnU-2L_xd0bsuVWpq86koo4fuh8MNj_PC0oXJHfAC0")
    @GET("student/wishList")
    fun getData(): Call<wishList_MyData>
}