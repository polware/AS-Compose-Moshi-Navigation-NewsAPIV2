package com.polware.newsapi2compose.ui.navigation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.polware.newsapi2compose.DateFormat
import com.polware.newsapi2compose.DateFormat.getTimeAgo
import com.polware.newsapi2compose.R
import com.polware.newsapi2compose.models.Article
import com.polware.newsapi2compose.models.getAllCategories
import com.polware.newsapi2compose.network.NewsManager
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun Categories(onFetchCategory: (String) -> Unit = {}, newsManager: NewsManager) {
    val tabItems = getAllCategories()
    Column {
        LazyRow {
            items(tabItems.size) {
                val category = tabItems[it]
                CategoryTab(category = category.categoryName,
                    isSelected = newsManager.selectedCategory.value == category,
                    onFetchCategory = onFetchCategory)
            }
        }
        CategoriesContent(articles = newsManager.responseCategories.value.articles ?: listOf())
    }
}

@Composable
fun CategoryTab(category: String, isSelected: Boolean = false, onFetchCategory: (String)-> Unit) {
    val background = if (isSelected)
        colorResource(id = R.color.blue_bg)
        else colorResource(id = R.color.blue_sky)

    Surface(modifier = Modifier
        .padding(horizontal = 4.dp, vertical = 15.dp)
        .clickable {
            onFetchCategory(category)
        },
        shape = MaterialTheme.shapes.small,
        color = background
    ) {

        Text(
            text = category,
            style = MaterialTheme.typography.body2,
            color = Color.White,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
fun CategoriesContent(articles: List<Article>, modifier: Modifier = Modifier) {
    LazyColumn {
        items(articles) {
            article ->
            Card(
                modifier.padding(8.dp),
                border = BorderStroke(2.dp, colorResource(id = R.color.purple_200))
            ) {
                Row(
                    modifier
                        .fillMaxWidth()
                        .padding(8.dp)) {
                    GlideImage(imageModel = article.urlToImage,
                        modifier.size(100.dp),
                        error = ImageBitmap.imageResource(id = R.drawable.news),
                        placeHolder = ImageBitmap.imageResource(id = R.drawable.news)
                    )
                    Column(modifier.padding(8.dp)) {

                        Text(text = article.title!!,
                            fontWeight = FontWeight.Bold,
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis
                        )
                        Row(modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(
                                text = article.author?: "Not Available",
                                fontWeight = FontWeight.Light
                            )
                            Text(
                                text = DateFormat.stringToDate(article.publishedAt!!).getTimeAgo(),
                                fontWeight = FontWeight.Light
                            )
                        }
                    }
                }
            }
        }
    }
}