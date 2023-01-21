package com.polware.newsapi2compose.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Source
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomMenuNav(val route: String, val icon: ImageVector, val title: String) {

    object TopNews: BottomMenuNav("TopNews",
        icon = Icons.Outlined.Home, "Top News")
    object Categories: BottomMenuNav("Categories",
        icon = Icons.Outlined.Category, "Categories")
    object Sources: BottomMenuNav("Sources",
        icon = Icons.Outlined.Source, "Sources")

}
