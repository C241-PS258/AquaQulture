package com.dicoding.aquaculture.view.customview

import android.app.UiModeManager
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.dicoding.aquaculture.R

class CustomBulletPointView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val isNightMode = isNightModeEnabled(context)

    private val paint = Paint().apply {
        color = if (isNightMode) {
            ContextCompat.getColor(context, android.R.color.white)
        } else {
            ContextCompat.getColor(context, R.color.black)
        }
        style = Paint.Style.FILL
        isAntiAlias = true
    }
    private val textPaint = TextPaint().apply {
        color = if (isNightMode) {
            ContextCompat.getColor(context, android.R.color.white)
        } else {
            ContextCompat.getColor(context, R.color.black)
        }
        textSize = 35f
        isAntiAlias = true
    }

    private val bulletRadius = 8f
    private val bulletMargin = 20f
    private val textMargin = 40f

    private var items: List<String> = emptyList()
    private val layouts = mutableListOf<StaticLayout>()

    fun setItems(items: List<String>) {
        this.items = items
        requestLayout()
        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val desiredWidth = MeasureSpec.getSize(widthMeasureSpec)
        var desiredHeight = paddingTop + paddingBottom

        layouts.clear()
        for (item in items) {
            val staticLayout = StaticLayout.Builder.obtain(item, 0, item.length, textPaint, desiredWidth - (paddingLeft + paddingRight + textMargin).toInt())
                .setAlignment(Layout.Alignment.ALIGN_NORMAL)
                .setLineSpacing(0f, 1f)
                .setIncludePad(false)
                .build()
            layouts.add(staticLayout)
            desiredHeight += staticLayout.height + bulletMargin.toInt()
        }

        setMeasuredDimension(desiredWidth, desiredHeight)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        var yOffset = paddingTop

        for ((index, item) in items.withIndex()) {
            val firstLineBaseline = layouts[index].getLineBaseline(0)
            val firstLineAscent = layouts[index].getLineAscent(0)
            val textCenterOffset = firstLineBaseline + (firstLineAscent / 2)

            val bulletCenterY = yOffset + textCenterOffset
            canvas.drawCircle(paddingLeft + bulletRadius,
                bulletCenterY.toFloat(), bulletRadius, paint)

            canvas.save()
            canvas.translate(paddingLeft + textMargin, yOffset.toFloat())
            layouts[index].draw(canvas)
            canvas.restore()

            yOffset += layouts[index].height + bulletMargin.toInt()
        }
    }

    private fun isNightModeEnabled(context: Context): Boolean {
        val uiModeManager = context.getSystemService(Context.UI_MODE_SERVICE) as? UiModeManager
        return uiModeManager?.nightMode == UiModeManager.MODE_NIGHT_YES
    }
}
