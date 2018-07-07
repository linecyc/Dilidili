package com.linecy.dilidili.ui.misc

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Shader.TileMode
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import java.security.MessageDigest

/**
 * 上半部分圆角矩形，下半部分矩形
 */
class GlideHalfRoundTransform(dp: Int = 4) : BitmapTransformation() {
  private var radius = 0f

  val id: String
    get() = javaClass.name + Math.round(radius)

  init {
    this.radius = Resources.getSystem().displayMetrics.density * dp
  }

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
    val rectF = RectF(0f, 0f, source.width.toFloat(), source.height.toFloat())
    canvas.drawRoundRect(rectF, radius, 0f, paint)
    return result
  }
}