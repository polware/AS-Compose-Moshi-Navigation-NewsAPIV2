package com.polware.newsapi2compose.ui.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.polware.newsapi2compose.R
import com.polware.newsapi2compose.models.Article
import com.polware.newsapi2compose.network.NewsManager

@Composable
fun Sources(newsManager: NewsManager) {
    val menuItems = listOf(
        "ABC News" to "abc-news",
        "TechCrunch" to "techcrunch",
        "talkSPORT" to "talksport",
        "Reuters" to "reuters",
        "The Verge" to "the-verge"
    )

    Scaffold(topBar = {
        TopAppBar(title = { Text(text = "${newsManager.sourceName.value} source")},
            actions = {
                var menuExpanded by remember {
                    mutableStateOf(false)
                }
                IconButton(onClick = { menuExpanded = true }) {
                    Icon(Icons.Default.MoreVert, contentDescription = null)
                }
                 MaterialTheme(
                     shapes = MaterialTheme.shapes.copy(medium = RoundedCornerShape(16.dp))
                 ){
                     DropdownMenu(expanded = menuExpanded,
                         onDismissRequest = { menuExpanded = false }) {
                         menuItems.forEach {
                             DropdownMenuItem(onClick = {
                                 // El segundo valor de "menuItems" se usa con la API
                                 newsManager.sourceName.value = it.second
                                 menuExpanded = false
                             }) {
                                 // Asignamos el primer valor de la lista "menuItems"
                                 Text(text = it.first)
                             }
                         }
                     }
                 }
            }
        )
    }) {
        newsManager.getArticlesBySources()
        val articlesList = newsManager.responseSources.value
        SourceContent(articles = articlesList.articles?: listOf())
    }
}

@Composable
fun SourceContent(articles: List<Article>) {
    val uriHandler = LocalUriHandler.current
    LazyColumn {
        items(articles) {
            article ->
            val annotatedString = buildAnnotatedString {
                pushStringAnnotation(
                    tag = "URL",
                    annotation = article.url ?: "newsapi.org"
                )
                withStyle(style = SpanStyle(color = colorResource(id = R.color.blue_sky),
                    textDecoration = TextDecoration.Underline)
                ) {
                    append("Read full article")
                }
            }
            Card(backgroundColor = colorResource(id = R.color.blue_sky),
                elevation = 6.dp, modifier = Modifier.padding(8.dp)
            ) {
                Column(modifier = Modifier
                    .height(200.dp)
                    .padding(start = 8.dp, end = 8.dp),
                verticalArrangement = Arrangement.SpaceEvenly) {
                    
                    Text(text = article.title!!,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.yellow),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                    )
                    Text(text = article.description!!,
                        color = colorResource(id = R.color.white),
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                    Card(backgroundColor = Color.White, elevation = 6.dp) {
                        ClickableText(text = annotatedString,
                            modifier = Modifier.padding(8.dp),
                            onClick = {
                                annotatedString.getStringAnnotations(it, it).firstOrNull()?.let {
                                    result ->
                                    if (result.tag == "URL") {
                                        // Open the URL in browser
                                        uriHandler.openUri(result.item)
                                    }
                                }
                            })
                    }
                }
            }
        }
    }
}