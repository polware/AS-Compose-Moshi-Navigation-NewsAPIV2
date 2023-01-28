package com.polware.newsapi2compose.models

data class TopNewsResponse(
    val status: String? = null,
    val totalResults: Int? = null,
    val articles: List<Article>? = null
)
