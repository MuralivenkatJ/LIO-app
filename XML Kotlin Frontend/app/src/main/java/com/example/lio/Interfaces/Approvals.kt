package com.example.lio.Interfaces
import com.example.lio.Models.Institute.myData_Approvals
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface Approvals {
    @Headers("Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpX2lkIjoiNjJiZmZhNTkxMjg0N2NlODg0NWVjYTQwIiwiaWF0IjoxNjU3OTg5MzUzLCJleHAiOjE2NTg0MjEzNTN9.bEANImWaBY6IA9YU9ZOAPa_sgzP45ej0C_1b_2sxiMo")
    @GET("institute/approvals")
    fun getData():Call<myData_Approvals>
}