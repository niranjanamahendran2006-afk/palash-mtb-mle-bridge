# Model assets folder (empty in the prototype)

This folder is where the ML/NLP lead's real quantized model files eventually go:

- `nllb_hi_sat_int8.tflite` (or `.onnx`) — fine-tuned, quantized translation model
- `vosk-model-hi/` or `whisper-tiny-hi.tflite` — Hindi ASR model
- `mms_tts_sat.tflite` — Santhali TTS model

The prototype ships with **no real model files** — `MockTranslationEngine`,
`MockSpeechRecognitionEngine`, and `MockSpeechSynthesisEngine` do not read from
this folder. Once real files are added here, update `OfflineContentRepository`
and swap the `Mock*Engine` constructions in the screens/ViewModels for the real
implementations (see `TODO` comments in `data/repository/`).

Model files are excluded from git via `.gitignore` — use Git LFS or a small
download script for anything over a few MB.
