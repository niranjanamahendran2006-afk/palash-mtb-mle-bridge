package com.palash.mtbmle

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.palash.mtbmle.data.repository.OfflineContentRepository
import com.palash.mtbmle.ui.screens.splash.SplashScreen
import com.palash.mtbmle.ui.theme.PalashTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val offlineContentRepository = OfflineContentRepository(applicationContext)

        setContent {
            PalashRoot(offlineContentRepository)
        }
    }
}

@Composable
private fun PalashRoot(offlineContentRepository: OfflineContentRepository) {
    var setupReady by remember { mutableStateOf(false) }
    var hasEntered by remember { mutableStateOf(false) }
    var statusLabel by remember { mutableStateOf("Getting things ready") }

    LaunchedEffect(Unit) {
        if (offlineContentRepository.isInitialSetupCompleted.first()) {
            statusLabel = "Ready to explore"
        } else {
            statusLabel = "Welcome"
            delay(500)
            statusLabel = "Preparing offline classroom content"
            delay(700)
            statusLabel = "Loading demo translation data"
            delay(500)
            statusLabel = "Loading worksheet templates"
            delay(500)
            statusLabel = "Demo content ready"
            delay(500)
            offlineContentRepository.markInitialSetupCompleted()
        }
        setupReady = true
    }

    PalashTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            AnimatedContent(
                targetState = setupReady && hasEntered,
                transitionSpec = {
                    (fadeIn() + scaleIn(initialScale = 0.96f)).togetherWith(fadeOut())
                },
                label = "splash to app transition"
            ) { isReady ->
                if (isReady) {
                    PalashApp()
                } else {
                    SplashScreen(
                        statusLabel = statusLabel,
                        onTap = { hasEntered = true }
                    )
                }
            }
        }
    }
}
