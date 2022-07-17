package com.example.lio.Models.Student

data class MyCourses_Enrolled(
    val _id: String,
    val assignment_marks: List<Int>,
    val course: MyCourses_Course,
    val date: String,
    val quiz_marks: List<MyCourses_QuizMark>,
    val status: String,
    val watched: List<Int>
)