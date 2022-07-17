package com.example.lio.Interfaces


import com.example.mycourses_lecturer.MyFaculty_MyData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface MyCourses_Faculty {

    @GET("faculty/mycourses")
    fun getData(
        @Header("Authorization") auth: String
    ):Call<MyFaculty_MyData>
}