package com.example.lio.Interfaces

import retrofit2.http.GET

interface Main
{

    @GET("course/unenrolled/{c_id}")
    fun enroll()
}