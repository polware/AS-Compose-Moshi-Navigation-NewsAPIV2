package com.polware.newsapi2compose.models

enum class ArticleCategory(val categoryName: String) {
    BUSINESS("Business"),
    ENTERTAINMENT("Entertainment"),
    GENERAL("General"),
    HEALTH("Health"),
    SCIENCE("Science"),
    SPORTS("Sports"),
    TECHNOLOGY("Technology")
}

fun getAllCategories(): List<ArticleCategory> {
    return listOf(ArticleCategory.BUSINESS, ArticleCategory.ENTERTAINMENT,
        ArticleCategory.GENERAL, ArticleCategory.HEALTH, ArticleCategory.SCIENCE,
        ArticleCategory.SPORTS, ArticleCategory.TECHNOLOGY)
}

fun getArticleCategory(category: String): ArticleCategory? {
    val map = ArticleCategory.values().associateBy(ArticleCategory::categoryName)
    return map[category]
}