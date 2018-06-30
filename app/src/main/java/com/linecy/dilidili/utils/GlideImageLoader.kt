package com.linecy.dilidili.utils

import android.content.Context
import android.widget.ImageView
import com.linecy.dilidili.data.model.Banner
import com.linecy.dilidili.di.module.GlideApp
import com.youth.banner.loader.ImageLoader


class GlideImageLoader : ImageLoader() {
  override fun displayImage(context: Context, path: Any, imageView: ImageView) {
    if (path is Banner) {
      GlideApp.with(context.applicationContext)
          .load(path.srcUrl)
          //.transition()
          .into(imageView)
    }
  }
}
