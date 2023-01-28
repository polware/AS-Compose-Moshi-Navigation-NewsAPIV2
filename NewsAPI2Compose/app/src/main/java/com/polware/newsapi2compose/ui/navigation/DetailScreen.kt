package com.polware.newsapi2compose.ui.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.polware.newsapi2compose.DateFormat
import com.polware.newsapi2compose.DateFormat.getTimeAgo
import com.polware.newsapi2compose.R
import com.polware.newsapi2compose.models.Article
import com.skydoves.landscapist.glide.GlideImage

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DetailScreen(navController: NavController, article: Article,
                 scrollState: ScrollState) {

    Scaffold(topBar = {
        DetailTopBar(onBackPressed = {navController.popBackStack()})
    }) {

        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally) {

            GlideImage(
                imageModel = article.urlToImage,
                contentScale = ContentScale.Crop,
                error = ImageBitmap.imageResource(id = R.drawable.news),
                placeHolder = ImageBitmap.imageResource(id = R.drawable.news)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalAlignment = Alignment.Start) {
                InfoWithIcon(icon = Icons.Default.Edit, info = article.author?:"Not Available")
                InfoWithIcon(icon = Icons.Default.DateRange,
                    info = DateFormat.stringToDate(article.publishedAt!!).getTimeAgo())
            }
            Text(text = article.title!!, fontWeight = FontWeight.Bold)
            Text(text = article.description!!,
                modifier = Modifier.padding(top = 15.dp))
            /*Button(onClick = {
                navController.navigate("TopNews")
                //navController.popBackStack()
            }) {
                Text(text = "Go to top news + ${newsLocalData.author}")
            }*/
        }
    }
}

@Composable
fun DetailTopBar(onBackPressed: ()-> Unit = {}) {
    TopAppBar(title = {
        Text(text = "Detail Screen", fontWeight = FontWeight.SemiBold)
    },
        navigationIcon = {
            IconButton(onClick = { onBackPressed() }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
            }
        })
}

@Composable
fun InfoWithIcon(icon: ImageVector, info: String) {
    Row {
        Icon(icon, contentDescription = "Author",
            modifier = Modifier.padding(end = 8.dp),
            colorResource(id = R.color.purple_500))
        Text(text = info, maxLines = 1,
            overflow = TextOverflow.Ellipsis)
    }
}

@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    //DetailScreen(rememberNavController())
}