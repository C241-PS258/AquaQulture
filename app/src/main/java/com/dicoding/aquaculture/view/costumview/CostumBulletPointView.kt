package com.dicoding.aquaculture.view.costumview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat

class CustomBulletPointView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint().apply {
        color = ContextCompat.getColor(context, android.R.color.black)
        style = Paint.Style.FILL
        isAntiAlias = true
    }
    private val textPaint = Paint().apply {
        color = ContextCompat.getColor(context, android.R.color.black)
        textSize = 50f
        isAntiAlias = true
    }

    private val bulletRadius = 10f
    private val bulletMargin = 20f
    private val textMargin = 40f

    private var items: List<String> = emptyList()

    fun setItems(items: List<String>) {
        this.items = items
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        var yOffset = paddingTop + bulletRadius

        for (item in items) {
            canvas.drawCircle(paddingLeft + bulletRadius, yOffset, bulletRadius, paint)
            canvas.drawText(item, paddingLeft + textMargin, yOffset + (bulletRadius / 2), textPaint)
            yOffset += textPaint.textSize + bulletMargin
        }
    }
}