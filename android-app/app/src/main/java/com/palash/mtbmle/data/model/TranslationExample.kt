package com.palash.mtbmle.data.model

/**
 * A single Hindi -> Santhali sentence pair used by the prototype's mock
 * translation dataset (see data/mock/MockTranslationData.kt).
 *
 * IMPORTANT: These are DEMO pairs for UI development only, not linguistically
 * validated data. When Role C (Data/Backend) supplies a real, native-speaker
 * validated CSV (hindi,santhali,source), that dataset replaces this mock list —
 * the UI and ViewModel do not need to change, only the data source.
 */
data class TranslationExample(
    val hindi: String,
    val santhaliOlChiki: String,
    val santhaliDevanagari: String,   // NEW — transliteration teachers can read
    val isDemoContent: Boolean = true
)
