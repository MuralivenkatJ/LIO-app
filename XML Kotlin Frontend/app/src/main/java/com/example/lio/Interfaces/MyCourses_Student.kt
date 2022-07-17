package com.example.lio.Interfaces

import com.example.lio.Models.Student.MyStudentData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers


interface MyCourses_Student
{
    @Headers("Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzX2lkIjoiNjJiZmRlY2M1NDgxYjNhNTk2MWVjMzY4IiwiaWF0IjoxNjU4MDE1NzQ5LCJleHAiOjE2NTg0NDc3NDl9.UQ-qbUKQGW6_tFIOavvrjvCCwUHM3BO7R_o0fXU0dyA")
    @GET("student/mycourses")
    fun getData():Call<MyStudentData>
}