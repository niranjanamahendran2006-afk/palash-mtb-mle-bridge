# data/

Owned by: Data/Backend person (Role C)

Put the Hindi–Santhali parallel dataset here:
- `raw/` — sourced text with attribution (AI4Bharat, eBible/Santali, NCERT, etc.)
- `cleaned/` — deduplicated, validated pairs (hindi, santhali, source columns)
- `splits/` — train/val/test CSV or JSON (roadmap recommends ~80/10/10)

This is empty in the current prototype pass — the Android app uses a small
hardcoded mock list instead (see `android-app/.../data/mock/MockTranslationData.kt`).
