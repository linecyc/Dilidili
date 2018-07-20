package com.linecy.dilidili.ui.home

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.linecy.banner.BannerCreator
import com.linecy.dilidili.R
import com.linecy.dilidili.data.model.Banner
import com.linecy.dilidili.di.module.GlideApp
import com.linecy.dilidili.ui.misc.GlideRoundTransform

/**
 * @author by linecy.
 */
class AdCreator : BannerCreator<Banner> {
  override fun getLayoutResId(): Int {
    return R.layout.layout_banner
  }

  override fun onBindData(view: View?, data: Banner?, position: Int) {
    data?.let {
      val ivCover = view?.findViewById<ImageView>(R.id.ivCover)
      view?.findViewById<TextView>(R.id.tvDetail)?.text = data.title
      ivCover?.let {
        GlideApp.with(ivCover.context)
            .load(data.srcUrl)
            .transform(GlideRoundTransform())
            .into(ivCover)
      }
    }
  }

}