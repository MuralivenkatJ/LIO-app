package com.example.lio.Interfaces

import com.example.lio.Models.Student.MyStudentData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers


interface MyCourses_Student
{

    @GET("student/mycourses")
    fun getData(
        @Header("Authorization") auth: String
    ):Call<MyStudentData>
}