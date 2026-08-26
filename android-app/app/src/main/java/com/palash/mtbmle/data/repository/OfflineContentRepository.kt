package com.palash.mtbmle.data.repository

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.palash.mtbmle.data.model.OfflineStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "palash_settings")
private val INITIAL_SETUP_COMPLETED_KEY = booleanPreferencesKey("initial_setup_completed")

/**
 * Tracks the one-time "initial content sync" flow (roadmap Section 19).
 *
 * Prototype behaviour: does NOT download any real model/data. It simply shows a short
 * "Demo content ready" sequence once, then persists a flag locally so subsequent launches
 * skip straight to Home.
 *
 * TODO(ML/Data team): Replace the simulated delay in MainActivity's first-launch flow with
 * real model/dataset download-and-verify logic once real assets exist.
 */
class OfflineContentRepository(private val context: Context) {

    val isInitialSetupCompleted: Flow<Boolean> =
        context.dataStore.data.map { prefs -> prefs[INITIAL_SETUP_COMPLETED_KEY] ?: false }

    suspend fun markInitialSetupCompleted() {
        context.dataStore.edit { prefs -> prefs[INITIAL_SETUP_COMPLETED_KEY] = true }
    }

    /** Prototype always reports offline-ready since no real model files are bundled yet. */
    fun currentOfflineStatus(): OfflineStatus = OfflineStatus.OFFLINE_READY
}
