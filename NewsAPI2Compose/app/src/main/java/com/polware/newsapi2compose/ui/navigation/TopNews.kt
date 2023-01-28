package com.polware.newsapi2compose.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.polware.newsapi2compose.DateFormat
import com.polware.newsapi2compose.DateFormat.getTimeAgo
import com.polware.newsapi2compose.R
import com.polware.newsapi2compose.models.Article
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun TopNews(navController: NavController, articles: List<Article>) {
    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Top News",
            color = colorResource(id = R.color.white),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            fontSize = 26.sp,
            modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .background(color = colorResource(id = R.color.blue_bg)))
        /*Button(onClick = {
             navController.navigate("Detail")
        }) {
            Text(text = "Go to detail screen")
        }*/
        LazyColumn {
            items(articles.size) {
                index ->
                NewsItem(article = articles[index],
                onItemClick = {
                    navController.navigate("Detail/$index")
                }
                )
            }
        }
    }
}

@Composable
fun NewsItem(article: Article, onItemClick: ()-> Unit = {}) {
    Box(contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp)
            .clickable {
                onItemClick()
            }) {

        GlideImage(
            imageModel = article.urlToImage,
            contentScale = ContentScale.Crop,
            error = ImageBitmap.imageResource(id = R.drawable.news),
            placeHolder = ImageBitmap.imageResource(id = R.drawable.news)
        )

        Column(modifier = Modifier
            .wrapContentHeight()
            .padding(top = 6.dp, start = 15.dp),
            horizontalAlignment = Alignment.Start) {

            Text(text = DateFormat.stringToDate(article.publishedAt!!).getTimeAgo(),
                color = Color.White, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(90.dp))
            Text(text = article.title!!,
                color = Color.White, fontWeight = FontWeight.SemiBold)
            Text(text = article.description?:"Not Available",
                color = Color.White, fontWeight = FontWeight.Light)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TopNewsPreview() {
    //NewsItem(newsLocalData = NewsLocalData())
}