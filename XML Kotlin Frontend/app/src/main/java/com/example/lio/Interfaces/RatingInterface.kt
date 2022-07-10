package com.example.lio.Interfaces

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

interface ratingInterface {

    @FormUrlEncoded
    @Headers("Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzX2lkIjoiNjJiZmRlY2M1NDgxYjNhNTk2MWVjMzY4IiwiaWF0IjoxNjU3MTc5Njk2LCJleHAiOjE2NTc2MTE2OTZ9.LjwxQbvSRnCcHXZp7HEFaiasil4vzmpFI9V5tc73xo4")
    @POST("student/review")
    fun rating(
        @Field("rate") r: Int,
        @Field("desc") d: String,
        @Field("c_id") c: String
    ): Call<String>

}