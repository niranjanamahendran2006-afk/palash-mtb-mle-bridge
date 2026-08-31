package com.palash.mtbmle.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.palash.mtbmle.ui.screens.home.HomeScreen
import com.palash.mtbmle.ui.screens.settings.SettingsScreen
import com.palash.mtbmle.ui.screens.translate.TranslateScreen
import com.palash.mtbmle.ui.screens.voice.VoiceScreen
import com.palash.mtbmle.ui.screens.worksheet.WorksheetScreen
import com.palash.mtbmle.ui.screens.welcome.WelcomeScreen

@Composable
fun PalashNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Welcome.route) {

        composable(Screen.Welcome.route) {
            WelcomeScreen(
                onGetStarted = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Welcome.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(Screen.Home.route) {
            HomeScreen(
                onNavigateToTranslate = { navController.navigate(Screen.Translate.route) },
                onNavigateToVoice = { navController.navigate(Screen.Voice.route) },
                onNavigateToWorksheet = { navController.navigate(Screen.Worksheet.route) }
            )
        }
        composable(Screen.Translate.route) { TranslateScreen() }
        composable(Screen.Voice.route) { VoiceScreen() }
        composable(Screen.Worksheet.route) { WorksheetScreen() }
        composable(Screen.Settings.route) { SettingsScreen() }
    }
}
