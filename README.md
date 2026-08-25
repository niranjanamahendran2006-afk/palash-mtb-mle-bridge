# PALASH — Mother Tongue Learning Bridge

An AI-assisted translation and curriculum-generation prototype supporting Jharkhand's
PALASH Mother Tongue-Based Multilingual Education (MTB-MLE) programme.

## Problem

Jharkhand's PALASH programme is pedagogically effective, but is bottlenecked by a shortage
of teachers proficient in tribal languages (Ho, Mundari, Santhali). Most teachers assigned
to tribal-area schools are Hindi-medium trained. This project builds a technology bridge:
an offline Android application that lets a Hindi-speaking teacher deliver mother-tongue-based
instruction without prior fluency in the tribal language.

## Target users

Hindi-speaking primary school teachers in tribal-area schools, most of whom:
- have limited technical experience,
- use low-cost Android tablets (Android 9+, ~2 GB RAM),
- have unreliable or no internet access in the classroom.

## Prototype scope

- **Language pair implemented:** Hindi → Santhali only (Ho and Mundari are out of scope for
  this prototype, but the language selector is architected to support them later).
- **Translation engine:** mock/placeholder data only — see "Implemented vs. Planned" below.
- **Voice pipeline:** mock ASR → mock translation → mock TTS, wired end-to-end with realistic
  loading states (Listening / Translating / Speaking), but not a real ML pipeline yet.
- **Worksheets:** predefined mock templates tagged to 3 NIPUN Bharat learning outcomes.
- **Offline:** the app makes no network calls at all — every screen works with airplane mode on.

## Implemented in prototype vs. Planned / future integration

| Component | Status |
|---|---|
| 5-screen navigation (Home, Translate, Voice, Worksheets, Settings) | ✅ Implemented |
| Text translation UI, loading state, result card | ✅ Implemented (mock data) |
| Voice conversation UI with Listening/Translating/Speaking states | ✅ Implemented (mock pipeline) |
| Worksheet browsing + generator UI | ✅ Implemented (mock templates) |
| Settings screen with model info | ✅ Implemented (labelled "planned") |
| First-launch offline content sync flow | ✅ Implemented (simulated, no real download) |
| Error states (empty input, mic permission, failures) | ✅ Implemented |
| Clean architecture with swappable engine interfaces | ✅ Implemented |
| **NLLB-200 on-device translation model** | ⏳ Planned — see `model-training/` |
| **Vosk/Whisper-tiny Hindi speech recognition** | ⏳ Planned |
| **Meta MMS-TTS Santhali speech synthesis** | ⏳ Planned |
| **Real bilingual dataset (validated by native speaker)** | ⏳ Planned — see `data/` |
| **Real Python/Jinja2 worksheet PDF generator** | ⏳ Planned — see `worksheet-generator/` |
| **<3 second measured voice latency on physical device** | ⏳ Not yet benchmarked — no real model is running |

This distinction is intentional and should not be blurred in any demo or pitch: the prototype
demonstrates **the application architecture and user experience**, not a working ML pipeline.

## Architecture

Clean, layered architecture so each team role can work independently (see `docs/team-guide.md`):

```
UI (Jetpack Compose screens)
   ↓
ViewModel (StateFlow-based UI state)
   ↓
Repository / Engine interfaces  ← the "contract" boundary
   ↓
Mock*Engine (prototype) → swap for Nllb/Vosk/MmsTts engines later
```

Key interfaces (in `android-app/app/src/main/java/com/palash/mtbmle/data/repository/`):
- `TranslationEngine` — Hindi text in, Santhali text out
- `SpeechRecognitionEngine` — audio in, Hindi text out
- `SpeechSynthesisEngine` — Santhali text in, audio out
- `VoiceTranslationEngine` — composes the three above into one pipeline call

None of the UI or ViewModel code depends on a concrete (`Mock` vs. real) implementation —
only on these interfaces. This is what lets Role B (Android) and Role A (ML/NLP) build in
parallel without blocking each other.

## Technology stack

- Kotlin, Jetpack Compose, Material 3
- Navigation Compose, ViewModel + StateFlow
- Jetpack DataStore (local offline settings/flags — no backend, no Firebase)
- No network calls, no cloud AI APIs

## Repository structure

```
PALASH-mtb-mle-bridge/
├── android-app/              <- this prototype (Role B)
│   └── app/src/main/java/com/palash/mtbmle/
├── data/                     <- Hindi-Santhali dataset work (Role C)
├── model-training/           <- NLLB fine-tuning notebooks (Role A)
├── worksheet-generator/      <- Python/Jinja2 worksheet generator (Role C)
├── docs/                     <- design files, architecture notes (Role D)
├── demo/                     <- demo video, pitch deck (Role E)
└── README.md                 <- this file
```

## How to run

1. Install [Android Studio](https://developer.android.com/studio) (Hedgehog or newer).
2. Open the `android-app/` folder as a project (not the repo root).
3. Let Gradle sync (first sync downloads dependencies — needs internet once, for setup only).
4. Run on an emulator (API 28+) or a physical device via `Run ▶`.
5. To test the offline claim: install the app once, then enable Airplane Mode and relaunch —
   every screen should work identically.

## How to build an APK

In Android Studio: `Build → Build Bundle(s) / APK(s) → Build APK(s)`.
Or from the command line inside `android-app/`: `./gradlew assembleDebug`
(requires the Gradle wrapper — see note below).

> **Note:** This repository ships Gradle config files but not the Gradle wrapper binary
> (`gradlew`/`gradle-wrapper.jar`), since those are normally generated by Android Studio on
> first open rather than hand-written. Opening the project in Android Studio will offer to
> generate the wrapper automatically, or run `gradle wrapper` once Gradle is installed locally.

## Limitations

- No real translation, speech recognition, or speech synthesis model is integrated yet —
  everything is mock data, clearly labelled as such in the UI and code.
- The <3 second latency target is a design target from the roadmap, not a measured result.
- Only Hindi→Santhali is implemented; Ho and Mundari are future work.
- The worksheet generator produces in-app mock previews only, not exported PDF/PNG files yet.
- No automated tests are included in this prototype pass.

## Data sources

See `model-training/` and `data/` for planned sourcing (AI4Bharat, CIIL Mysore, eBible/Santali,
NCERT/Jharkhand SCERT curriculum, and the 2025 ACL paper on NLLB/mBART adaptation for Mundari
and Santali). No real dataset is bundled in this prototype pass.

## Team responsibilities

| Role | Owns |
|---|---|
| ML/NLP Lead | Translation model fine-tuning, ASR/TTS integration |
| Android/Mobile Developer | This app — offline shell, TFLite integration, on-device inference |
| Data/Backend | Dataset curation, worksheet/flashcard generation logic |
| UI/UX & Frontend | Teacher-facing screen designs |
| Outreach & Documentation | Native-speaker validation, demo video, README, pitch deck |
