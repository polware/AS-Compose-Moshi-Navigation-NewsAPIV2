package com.polware.newsapi2compose.ui.components

import android.util.Log
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.polware.newsapi2compose.models.Article
import com.polware.newsapi2compose.network.NewsManager
import com.polware.newsapi2compose.ui.navigation.*

@Composable
fun NewsApp() {
    val navController = rememberNavController()
    val scrollState = rememberScrollState()
    MainScreen(navHostController = navController , scrollState = scrollState)
}

@Composable
fun MainScreen(navHostController: NavHostController, scrollState: ScrollState) {
    Scaffold(bottomBar = {
        BottomMenuUI(navHostController)
    }) {
        Navigation(navHostController = navHostController, scrollState = scrollState,
            paddingValues = it)
    }
}

@Composable
fun Navigation(navHostController: NavHostController, scrollState: ScrollState,
               newsManager: NewsManager = NewsManager(), paddingValues: PaddingValues) {

    val articles = newsManager.newsResponse.value.articles
    Log.i("Articles: ", "$articles")
    articles?.let {
        NavHost(navController = navHostController,
            startDestination = BottomMenuNav.TopNews.route,
            modifier = Modifier.padding(paddingValues = paddingValues)) {

            bottomNavigation(navController = navHostController, articles = articles,
                newsManager = newsManager)

            composable("Detail/{index}",
                arguments = listOf(navArgument("index"){ type = NavType.IntType })
            ) {
                navBackStackEntry ->
                val index = navBackStackEntry.arguments?.getInt("index")
                index?.let {
                    val article = articles[index]
                    DetailScreen(navController = navHostController, article, scrollState)
                }
            }
        }
    }
}

fun NavGraphBuilder.bottomNavigation(navController: NavController, articles: List<Article>,
                                     newsManager: NewsManager) {
    composable(BottomMenuNav.TopNews.route) {
        TopNews(navController = navController, articles)
    }
    composable(BottomMenuNav.Categories.route) {
        newsManager.onSelectedCategory("Business")
        newsManager.getArticlesByCategory("Business")
        Categories(newsManager = newsManager, onFetchCategory = {
            newsManager.onSelectedCategory(it)
            newsManager.getArticlesByCategory(it)
        })
    }
    composable(BottomMenuNav.Sources.route) {
        Sources(newsManager = newsManager)
    }
}