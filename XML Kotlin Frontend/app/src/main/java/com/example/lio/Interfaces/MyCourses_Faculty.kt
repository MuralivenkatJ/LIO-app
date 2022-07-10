package com.example.lio.Interfaces


import com.example.mycourses_lecturer.MyFaculty_MyData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface MyCourses_Faculty {
    @Headers("Authorization:Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJmX2lkIjoiNjJiZmZkMGYxMjg0N2NlODg0NWVjYTQ0IiwiaWF0IjoxNjU3MTAyMjcwLCJleHAiOjE2NTc1MzQyNzB9.dyrkaaFdDoqWGEZUoKu39oaqIXqUL0D9YRFAurJ9S1s")
    @GET("faculty/mycourses")
    fun getData():Call<MyFaculty_MyData>
}