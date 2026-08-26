# model-training/

Owned by: ML/NLP Lead (Role A)

Put here:
- `finetune_nllb.ipynb` — Colab notebook fine-tuning NLLB-200 on `../data/cleaned/`
- `quantize_export.py` — converts the fine-tuned model to TFLite/ONNX (INT8)
- `eval_report.md` — BLEU/chrF scores + native-speaker review notes

Export the final quantized model into
`../android-app/app/src/main/assets/models/` when ready.
