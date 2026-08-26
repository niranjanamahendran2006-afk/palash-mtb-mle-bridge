package com.palash.mtbmle

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.palash.mtbmle.data.repository.OfflineContentRepository
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

/**
 * Decides between the one-time "initial content sync" flow (roadmap Section 19) and
 * going straight to the main app. Prototype does NOT download real models — it just
 * shows a short demo sequence once, then persists a flag so future launches skip it.
 */
@Composable
private fun PalashRoot(offlineContentRepository: OfflineContentRepository) {
    var isCheckingSetup by remember { mutableStateOf(true) }
    var setupCompleted by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        setupCompleted = offlineContentRepository.isInitialSetupCompleted.first()
        isCheckingSetup = false
    }

    PalashTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            when {
                isCheckingSetup -> { /* brief flag check, no UI needed */ }
                !setupCompleted -> FirstLaunchSyncScreen(
                    onFinished = {
                        setupCompleted = true
                    },
                    offlineContentRepository = offlineContentRepository
                )
                else -> PalashApp()
            }
        }
    }
}

@Composable
private fun FirstLaunchSyncScreen(
    onFinished: () -> Unit,
    offlineContentRepository: OfflineContentRepository
) {
    var stepLabel by remember { mutableStateOf("Welcome") }

    LaunchedEffect(Unit) {
        stepLabel = "Welcome"
        delay(500)
        stepLabel = "Preparing offline classroom content"
        delay(700)
        stepLabel = "Loading demo translation data"
        delay(500)
        stepLabel = "Loading worksheet templates"
        delay(500)
        stepLabel = "Demo content ready"
        delay(500)
        offlineContentRepository.markInitialSetupCompleted()
        onFinished()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("PALASH", style = MaterialTheme.typography.headlineLarge)
        Text("Mother Tongue Learning Bridge", style = MaterialTheme.typography.titleMedium)
        androidx.compose.foundation.layout.Spacer(Modifier.padding(12.dp))
        CircularProgressIndicator()
        androidx.compose.foundation.layout.Spacer(Modifier.padding(12.dp))
        Text(stepLabel, style = MaterialTheme.typography.bodyLarge)
    }
}
