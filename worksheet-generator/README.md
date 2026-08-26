# worksheet-generator/

Owned by: Data/Backend person (Role C)

Put here:
- `templates/` — Jinja2 templates for bilingual worksheets
- `generate.py` — fills templates with Hindi/Santhali content, exports PDF/PNG

Output files should be referenced from `Worksheet.filePath` once wired into
the Android app's `WorksheetRepository`.
