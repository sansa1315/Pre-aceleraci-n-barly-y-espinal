package com.alkemy.challengue

data class MostPopularData(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)