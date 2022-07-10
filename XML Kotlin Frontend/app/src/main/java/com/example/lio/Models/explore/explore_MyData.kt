package com.example.explore_page

data class explore_MyData(
    val guided_project: List<explore_MostViewed>,
    val most_viewed: List<explore_MostViewed>,
    val recently_launched: List<explore_MostViewed>,
    val specialization: List<String>
)