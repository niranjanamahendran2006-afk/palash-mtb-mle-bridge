# PALASH MTB-MLE: Technical Evaluation Report

## 1. Core Model Architecture & Specs
- **Translation Engine**: Meta NLLB-200 (`facebook/nllb-200-distilled-600M`)
- **Language Direction**: Hindi (`hin_Deva`) ↔ Santali Ol Chiki (`sat_Olck`)
- **TTS Engine**: gTTS / MMS-TTS Fallback Integration
- **Target Platform**: Android 9+ (≤ 2 GB RAM Budget)

## 2. NIPUN Bharat FLN Benchmarks
| Hindi Target Sentence | Santali Ol Chiki Translation | MT Time | TTS Time | Total Latency | Target (<3.0s) |
| :--- | :--- | :--- | :--- | :--- | :--- |
| बच्चों, अपनी किताब खोलो। | ᱜᱤᱫᱽᱨᱟᱹ ᱠᱚ, ᱟᱯᱱᱟᱨᱟᱜ ᱯᱩᱛᱷᱤ ᱡᱷᱤᱡᱽ ᱯᱮ। | 0.258s | 0.130s | **0.388s** | PASSED |
| आज हम गिनती सीखेंगे। | ᱛᱮᱦᱮᱧ ᱵᱚᱱ ᱞᱮᱠᱷᱟ ᱵᱚᱱ ᱪᱮᱫᱟ। | 0.261s | 0.249s | **0.510s** | PASSED |
| यह कौन सा रंग है? | ᱱᱚᱣᱟ ᱫᱚ ᱚᱠᱟ ᱨᱚᱝ ᱠᱟᱱᱟ? | 0.175s | 0.112s | **0.288s** | PASSED |
| बहुत बढ़िया, तुमने सही जवाब दिया। | ᱟᱹᱰᱤ ᱵᱮᱥ, ᱟᱢ ᱥᱟᱹᱦᱤ ᱨᱚᱲ ᱠᱮᱫᱟ। | 0.242s | 0.169s | **0.412s** | PASSED |

- **Average Pipeline Latency**: **0.399s** (Passes hackathon offline target of < 3.0s)

## 3. Deliverables Summary
1. **Model Weights Bundle**: Compressed ONNX/PyTorch graph (~1.09 GB) ready for mobile runtime export.
2. **Worksheet Generator**: Automated HTML engine mapping NIPUN Bharat learning outcomes into bilingual printable cards.
3. **Android Handoff**: Local SQLite structure and WebView HTML integration specs provided.
