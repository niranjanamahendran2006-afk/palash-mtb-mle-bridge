package com.palash.mtbmle

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.palash.mtbmle.navigation.PalashNavGraph
import com.palash.mtbmle.navigation.Screen
import com.palash.mtbmle.ui.theme.PalashTheme
import com.palash.mtbmle.ui.components.CosmicBackground
import com.palash.mtbmle.ui.theme.CosmicCyan
import com.palash.mtbmle.ui.theme.CosmicTextMuted

/**
 * App root: bottom navigation (Home / Translate / Voice / Worksheets / Settings) + nav graph.
 * Simple, recognizable navigation per roadmap Section 5 — no drawers, no nested menus.
 */
@Composable
fun PalashApp() {
    PalashTheme {
        val navController = rememberNavController()

        CosmicBackground(accentColor = CosmicCyan) {
        Scaffold(
            containerColor = androidx.compose.ui.graphics.Color.Transparent,
            bottomBar = {
                NavigationBar(containerColor = CosmicCyan.copy(alpha = 0.08f)) {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination

                    Screen.bottomNavItems.forEach { screen ->
                        val selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true
                        NavigationBarItem(
                            selected = selected,
                            onClick = {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = { androidx.compose.material3.Icon(screen.icon, contentDescription = screen.label) },
                            label = { Text(screen.label, color = if (selected) CosmicCyan else CosmicTextMuted) },
                            colors = androidx.compose.material3.NavigationBarItemDefaults.colors(
                                selectedIconColor = CosmicCyan,
                                unselectedIconColor = CosmicTextMuted,
                                indicatorColor = CosmicCyan.copy(alpha = 0.18f)
                            )
                        )
                    }
                }
            }
        ) { innerPadding ->
            androidx.compose.foundation.layout.Box(modifier = Modifier.padding(innerPadding)) {
                PalashNavGraph(navController = navController)
            }
        }
        }
    }
}
