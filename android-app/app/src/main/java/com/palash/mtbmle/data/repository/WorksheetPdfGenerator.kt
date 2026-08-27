#!/usr/bin/env kotlin
package com.palash.mtbmle.data.repository

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import com.palash.mtbmle.data.model.Worksheet
import java.io.File
import java.io.FileOutputStream

/**
 * Generates a real, printable PDF file for a Worksheet using Android's built-in
 * PdfDocument API. No external library, no ML, no internet — this is genuine,
 * working functionality, unlike the mock translation/voice engines elsewhere.
 */
class WorksheetPdfGenerator(private val context: Context) {

    private val pageWidth = 595   // A4 width in points
    private val pageHeight = 842  // A4 height in points

    fun generate(worksheet: Worksheet): File {
        val pdfDocument = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create()
        val page = pdfDocument.startPage(pageInfo)
        val canvas: Canvas = page.canvas

        val titlePaint = Paint().apply {
            color = Color.rgb(27, 94, 32)
            textSize = 20f
            isFakeBoldText = true
        }
        val subtitlePaint = Paint().apply {
            color = Color.DKGRAY
            textSize = 12f
        }
        val labelPaint = Paint().apply {
            color = Color.rgb(46, 125, 50)
            textSize = 11f
            isFakeBoldText = true
        }
        val hindiPaint = Paint().apply {
            color = Color.rgb(26, 35, 126)
            textSize = 15f
        }
        val olChikiPaint = Paint().apply {
            color = Color.rgb(46, 125, 50)
            textSize = 16f
            isFakeBoldText = true
        }
        val devanagariPaint = Paint().apply {
            color = Color.rgb(211, 84, 0)
            textSize = 13f
        }
        val linePaint = Paint().apply {
            color = Color.LTGRAY
            strokeWidth = 1f
        }

        var y = 50f
        val marginLeft = 40f

        canvas.drawText("PALASH — Bilingual FLN Worksheet", marginLeft, y, titlePaint)
        y += 22f
        canvas.drawText("Learning outcome: ${worksheet.learningOutcome.displayName}", marginLeft, y, subtitlePaint)
        y += 30f

        val count = minOf(
            worksheet.hindiContent.size,
            worksheet.santhaliOlChikiContent.size,
            worksheet.santhaliDevanagariContent.size
        )

        for (i in 0 until count) {
            canvas.drawText("Q${i + 1}", marginLeft, y, labelPaint)
            y += 18f
            canvas.drawText("Hindi: ${worksheet.hindiContent[i]}", marginLeft, y, hindiPaint)
            y += 20f
            canvas.drawText("Santhali (Ol Chiki): ${worksheet.santhaliOlChikiContent[i]}", marginLeft, y, olChikiPaint)
            y += 20f
            canvas.drawText("How to read it: ${worksheet.santhaliDevanagariContent[i]}", marginLeft, y, devanagariPaint)
            y += 16f
            canvas.drawLine(marginLeft, y + 20f, pageWidth - marginLeft, y + 20f, linePaint)
            y += 40f
        }

        pdfDocument.finishPage(page)

        val outputDir = File(context.filesDir, "worksheets")
        if (!outputDir.exists()) outputDir.mkdirs()
        val outputFile = File(outputDir, "${worksheet.id}.pdf")

        FileOutputStream(outputFile).use { out ->
            pdfDocument.writeTo(out)
        }
        pdfDocument.close()

        return outputFile
    }
}
