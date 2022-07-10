package com.example.lio.Models.Student

data class MyCourses_Course(
    val _id: String,
    val c_name: String,
    val myCoursesFaculty: MyCourses_Faculty,
    val image: String,
    val no_of_videos: Int,
    val rating: Int,
    val views: Int
)