package com.palash.mtbmle.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Translate
import androidx.compose.ui.graphics.vector.ImageVector

/** Every navigable destination in the app, in one place, per the roadmap's clean-architecture rule. */
sealed class Screen(val route: String, val label: String, val icon: ImageVector) {
    data object Home : Screen("home", "Home", Icons.Filled.Home)
    data object Translate : Screen("translate", "Translate", Icons.Filled.Translate)
    data object Voice : Screen("voice", "Voice", Icons.Filled.Mic)
    data object Worksheet : Screen("worksheet", "Worksheets", Icons.Filled.MenuBook)
    data object Settings : Screen("settings", "Settings", Icons.Filled.Settings)

    companion object {
        val bottomNavItems = listOf(Home, Translate, Voice, Worksheet, Settings)
    }
}
