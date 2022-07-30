package com.example.wishlist

data class wishList_Course(
    val __v: Int,
    val _id: String,
    val assignment_questions: List<String>,
    val c_name: String,
    val date: String,
    val description: String,
    val duration: String,
    val faculty: String,
    val guided_project: Boolean,
    val image: String,
    val level: String,
    val no_of_videos: Int,
    val playlistId: String,
    val price: Int,
    val quiz_questions: List<String>,
    val rating: Float,
    val wishListReviews: List<wishList_Review>,
    val skills: List<String>,
    val specialization: String,
    val views: Int
)