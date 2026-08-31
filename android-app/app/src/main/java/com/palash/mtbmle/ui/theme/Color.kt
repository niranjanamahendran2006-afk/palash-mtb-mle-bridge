package com.palash.mtbmle.ui.theme

import androidx.compose.ui.graphics.Color

val CosmicMidnight = Color(0xFF050817)
val CosmicNavy = Color(0xFF0B1230)
val CosmicCard = Color(0xFF121A3A)
val CosmicText = Color(0xFFF3F4FF)
val CosmicTextMuted = Color(0xFFB9C1E3)
val CosmicViolet = Color(0xFF9B76FF)
val CosmicCyan = Color(0xFF45D9FF)
val CosmicIndigo = Color(0xFF7C83FF)
val CosmicPink = Color(0xFFFF65B8)
val CosmicGold = Color(0xFFFFC857)
val CosmicMint = Color(0xFF65E0B0)

// Per-region galaxy accents (roadmap: "different galaxy for each screen").
// Every screen still renders on the same deep-space base (CosmicMidnight/CosmicNavy) —
// these only tint that region's nebula, glow and UI accents.
val CosmicBlue = Color(0xFF4C8DFF)       // Home region
val CosmicMagenta = Color(0xFFE0459F)    // Translate region
val CosmicTeal = Color(0xFF2FD9C4)       // Voice region
val CosmicOrange = Color(0xFFFF8A4C)     // Worksheets region
val CosmicCoral = Color(0xFFFF5F5F)      // Worksheets region (warm accent)

// A calm, education-appropriate palette — deliberately NOT chatbot/tech-startup styled.
val PalashGreenPrimary = Color(0xFF1E6E5C)
val PalashGreenDark = Color(0xFF124A3D)
val PalashAmberAccent = Color(0xFFD98E2E)
val PalashBackground = CosmicMidnight
val PalashSurface = CosmicCard
val PalashTextPrimary = CosmicText
val PalashTextSecondary = CosmicTextMuted
val PalashError = Color(0xFFB3261E)
val PalashOfflineReady = Color(0xFF2E7D32)