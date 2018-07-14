package com.linecy.dilidili.ui.misc

import android.graphics.Bitmap
import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Shader.TileMode
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import java.security.MessageDigest

class GlideCircleTransform : BitmapTransformation() {

  override fun transform(pool: BitmapPool, toTransform: Bitmap, outWidth: Int,
      outHeight: Int): Bitmap? {
    return roundCrop(pool, toTransform)
  }

  override fun updateDiskCacheKey(messageDigest: MessageDigest) {

  }


  private fun roundCrop(pool: BitmapPool, source: Bitmap?): Bitmap? {
    if (source == null) return null

    val result = pool.get(source.width, source.height, Bitmap.Config.ARGB_8888)

    val canvas = Canvas(result)
    val paint = Paint()
    paint.shader = BitmapShader(source, TileMode.CLAMP, TileMode.CLAMP)
    paint.isAntiAlias = true
    val size = if (source.width > source.height) source.height.toFloat() else source.width.toFloat()
    val rectF = RectF(0f, 0f, size, size)
    val radius = size / 2f
    canvas.drawRoundRect(rectF, radius, radius, paint)
    return result
  }
}