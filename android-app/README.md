# PALASH Android App — Developer Notes

This is the Android prototype only. See the repo root `README.md` for the full project
overview. This file explains what was built, file by file, and exactly where real ML
components plug in later.

## What was built (Phase 1–11 of the build order)

- **Navigation**: `navigation/Screen.kt` + `navigation/PalashNavGraph.kt` — 5 screens
  wired to a bottom navigation bar (`PalashApp.kt`).
- **Home screen**: `ui/screens/home/HomeScreen.kt` — dashboard with 3 large action cards
  and an offline-ready badge.
- **Text Translation screen**: `ui/screens/translate/TranslateScreen.kt` +
  `viewmodel/TranslateViewModel.kt` — input box, Translate button, loading state, result card.
- **Mock translation engine**: `data/repository/TranslationEngine.kt` (interface) +
  `data/repository/MockTranslationEngine.kt` (implementation) +
  `data/mock/MockTranslationData.kt` (demo sentence pairs).
- **Voice Conversation screen**: `ui/screens/voice/VoiceScreen.kt` +
  `viewmodel/VoiceViewModel.kt` — mic button, Listening/Translating/Speaking states,
  conversation history.
- **Mock ASR → translation → TTS pipeline**: `data/repository/SpeechRecognitionEngine.kt`,
  `SpeechSynthesisEngine.kt`, and `VoiceTranslationEngine.kt` (the orchestrator), each with
  a `Mock*` implementation.
- **Worksheet screen**: `ui/screens/worksheet/WorksheetScreen.kt` +
  `viewmodel/WorksheetViewModel.kt` + `data/repository/WorksheetRepository.kt` — outcome
  picker, generator, in-app worksheet preview.
- **Settings screen**: `ui/screens/settings/SettingsScreen.kt` — language pair, offline
  toggle, "Data & Model Information" section labelled as planned integrations.
- **Offline-ready local data layer**: `data/repository/OfflineContentRepository.kt` using
  Jetpack DataStore to persist the one-time first-launch flag — see `MainActivity.kt` for
  the "Welcome → Preparing content → Offline Ready" sequence.

## Where real ML components will be integrated

Every integration point is marked with a `TODO(...)` comment in code. Summary:

| Real component | Replaces | File to edit |
|---|---|---|
| NLLB-200 (quantized, on-device) | `MockTranslationEngine` | New `NllbTranslationEngine.kt` implementing `TranslationEngine`; swap the constructor call in `TranslateScreen.kt` and `VoiceScreen.kt` |
| Vosk / Whisper-tiny (Hindi ASR) | `MockSpeechRecognitionEngine` | New `VoskSpeechRecognitionEngine.kt` implementing `SpeechRecognitionEngine`; swap in `VoiceScreen.kt` |
| Meta MMS-TTS (Santhali) | `MockSpeechSynthesisEngine` | New `MmsTtsEngine.kt` implementing `SpeechSynthesisEngine`; swap in `VoiceScreen.kt` |
| Real Python/Jinja2 worksheet generator | `WorksheetRepository`'s mock list | Point `WorksheetRepository.generateWorksheet()` at real generated files (populate `Worksheet.filePath`) |
| Real Hindi–Santhali dataset | `MockTranslationData.kt` | Load a CSV from `data/cleaned/` instead of the hardcoded list |

**No screen, ViewModel, or navigation code needs to change for any of these swaps** — that's
the entire point of the interface boundary in `data/repository/`.

## How to run

See the root README's "How to run" section — open `android-app/` (this folder) directly
in Android Studio, not the repo root.

## Known prototype limitations

- All translations, transcriptions, and audio are mock/placeholder — never presented as
  real model output anywhere in the UI copy.
- Processing times shown in the Voice screen are simulated delays, not real inference
  benchmarks — labelled "Demo processing time" in the UI.
- No `gradlew` wrapper binary is checked in (see root README note) — Android Studio
  generates it on first project open.
- No unit/UI tests included in this pass.
