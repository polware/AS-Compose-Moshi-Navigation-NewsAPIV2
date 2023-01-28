package com.polware.newsapi2compose.network

import android.util.Log
import androidx.compose.runtime.*
import com.polware.newsapi2compose.models.ArticleCategory
import com.polware.newsapi2compose.models.TopNewsResponse
import com.polware.newsapi2compose.models.getArticleCategory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsManager {

    private val _newsResponse = mutableStateOf(TopNewsResponse())
    // When the data changes, State will update the UI automatically
    val newsResponse: State<TopNewsResponse>
        @Composable get() = remember {
            _newsResponse
        }

    private val _responseCategories = mutableStateOf(TopNewsResponse())
    val responseCategories: MutableState<TopNewsResponse>
        @Composable get() = remember {
            _responseCategories
        }

    val selectedCategory: MutableState<ArticleCategory?> = mutableStateOf(null)

    val sourceName = mutableStateOf("abc-news")
    private val _responseSources = mutableStateOf(TopNewsResponse())
    val responseSources: MutableState<TopNewsResponse>
        @Composable get() = remember {
            _responseSources
        }

    init {
        getArticles()
    }

    private fun getArticles() {
        val service = ApiClient.retrofitService.getNewsTopHeadlines("us")
        service.enqueue(object : Callback<TopNewsResponse> {

            override fun onResponse(call: Call<TopNewsResponse>, response:
            Response<TopNewsResponse>
            ) {
                if (response.isSuccessful) {
                    _newsResponse.value = response.body()!!
                    Log.i("NewsResponse: ", "${_newsResponse.value}")
                }
                else {
                    Log.e("Response Error:", "${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<TopNewsResponse>, t: Throwable) {
                Log.e("GetArticles Error:", "${t.printStackTrace()}")
            }
        })
    }

    fun onSelectedCategory(category: String) {
        val newCategory = getArticleCategory(category = category)
        selectedCategory.value = newCategory
    }

    fun getArticlesByCategory(category: String) {
        val service = ApiClient.retrofitService.getArticlesByCategory(category)
        service.enqueue(object : Callback<TopNewsResponse> {

            override fun onResponse(call: Call<TopNewsResponse>, response:
            Response<TopNewsResponse>
            ) {
                if (response.isSuccessful) {
                    _responseCategories.value = response.body()!!
                    Log.i("CategoryResponse: ", "${_responseCategories.value}")
                }
                else {
                    Log.e("Response Error:", "${response.code()}")
                }
            }

            override fun onFailure(call: Call<TopNewsResponse>, t: Throwable) {
                Log.e("ArticlesCategory Error:", "${t.printStackTrace()}")
            }
        })
    }

    fun getArticlesBySources() {
        val service = ApiClient.retrofitService.getArticlesBySource(sourceName.value)
        service.enqueue(object : Callback<TopNewsResponse> {

            override fun onResponse(call: Call<TopNewsResponse>, response:
            Response<TopNewsResponse>
            ) {
                if (response.isSuccessful) {
                    _responseSources.value = response.body()!!
                    Log.i("SourcesResponse: ", "${_responseSources.value}")
                }
                else {
                    Log.e("Response Error:", "${response.code()}")
                }
            }

            override fun onFailure(call: Call<TopNewsResponse>, t: Throwable) {
                Log.e("ArtSources Error:", "${t.printStackTrace()}")
            }
        })
    }

}