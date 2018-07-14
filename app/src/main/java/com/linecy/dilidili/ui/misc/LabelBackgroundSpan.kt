package com.linecy.dilidili.ui.misc

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Paint.FontMetricsInt
import android.support.annotation.ColorInt
import android.text.TextUtils
import android.text.style.ReplacementSpan
import android.util.TypedValue


/**
 * @author by linecy.
 */
class LabelBackgroundSpan(context: Context,
    private val textString: String?, @ColorInt private val bgColor: Int,
    textSize: Float = 10f, @ColorInt private val textColor: Int = Color.BLACK,
    textPadding: Float = 4f) : ReplacementSpan() {


  private var bgWidth: Int = 0
  private val padding: Float = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, textPadding,
      context.resources.displayMetrics)
  private val size = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, textSize,
      context.resources.displayMetrics)

  override fun getSize(paint: Paint?, text: CharSequence?, start: Int, end: Int,
      fm: FontMetricsInt?): Int {
    bgWidth = if (paint != null) {
      paint.textSize = size
      ((paint.measureText(textString).plus(2 * padding)).toInt())

    } else {
      0
    }
    return bgWidth + 10
  }

  override fun draw(canvas: Canvas?, text: CharSequence?, start: Int, end: Int, x: Float, top: Int,
      y: Int, bottom: Int, paint: Paint?) {
    if (null != paint && null != canvas && !TextUtils.isEmpty(textString)) {
      paint.color = bgColor
      paint.textSize = size
      paint.isAntiAlias = true
      paint.style = Paint.Style.STROKE
      paint.strokeWidth = 2f
      val fontMetrics = paint.fontMetrics
      val baseline = (bottom - top - fontMetrics.bottom - fontMetrics.top) / 2
//      canvas.drawRect(x, top.toFloat(),
//          x + bgWidth,
//          bottom.toFloat(),
//          paint)

      canvas.drawRoundRect(x, top.toFloat(),
          x + bgWidth,
          bottom.toFloat(), 6f, 6f, paint)
      paint.color = textColor
      paint.style = Paint.Style.FILL
      canvas.drawText(textString, x + padding, baseline,
          paint)
    }
  }
}