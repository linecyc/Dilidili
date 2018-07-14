package com.linecy.dilidili.utils

import android.databinding.BindingAdapter
import android.support.v4.content.ContextCompat
import android.text.SpannableString
import android.text.Spanned
import android.text.TextUtils
import android.widget.ImageView
import android.widget.TextView
import com.linecy.dilidili.R
import com.linecy.dilidili.data.model.Cartoon
import com.linecy.dilidili.data.model.Label
import com.linecy.dilidili.di.module.GlideApp
import com.linecy.dilidili.ui.misc.GlideHalfRoundTransform
import com.linecy.dilidili.ui.misc.GlideRoundTransform
import com.linecy.dilidili.ui.misc.LabelBackgroundSpan


/**
 *
 * @author by linecy.
 */
object BindingUIUtil {

  @JvmStatic
  @BindingAdapter("loadPicture")
  fun setPicture(imageView: ImageView, url: String?) {
    url?.let {
      GlideApp.with(imageView.context)
          .load(it)
          .transform(GlideRoundTransform())
          .into(imageView)
    }
  }

  @JvmStatic
  @BindingAdapter("loadPicture")
  fun setPicture(imageView: ImageView, url: Int) {
    GlideApp.with(imageView.context)
        .load(url)
        .transform(GlideRoundTransform())
        .into(imageView)
  }

  @JvmStatic
  @BindingAdapter("loadSearchPicture")
  fun setSearchPicture(imageView: ImageView, url: String?) {
    if (TextUtils.isEmpty(url)) {
      GlideApp.with(imageView.context)
          .load(R.drawable.ic_thumb_bg)
          .transform(GlideRoundTransform())
          .into(imageView)
    } else {
      GlideApp.with(imageView.context)
          .load(url)
          .transform(GlideRoundTransform())
          .into(imageView)
    }
  }

  @JvmStatic
  @BindingAdapter("loadHalfRoundPicture")
  fun setHalfPicture(imageView: ImageView, url: String?) {
    url?.let {
      GlideApp.with(imageView.context)
          .load(it)
          .transform(GlideHalfRoundTransform())
          .into(imageView)
    }
  }

  @JvmStatic
  @BindingAdapter("showLabel")
  fun setLabel(textView: TextView, cartoon: Cartoon) {
    val spannable = SpannableString(" " + cartoon.currentTitle)
    val color = ContextCompat.getColor(textView.context, R.color.colorPrimary)
    val span = LabelBackgroundSpan(context = textView.context, textString = cartoon.latest,
        bgColor = color, textColor = color
    )
    spannable.setSpan(span, 0, 1,
        Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
    textView.text = spannable
  }

  @JvmStatic
  @BindingAdapter("showLabel")
  fun setLabel(textView: TextView, labels: List<Label>?) {

    val stringBuilder = StringBuilder("标签：")
    labels?.forEach {
      stringBuilder.append(it.name+" ")
    }

    textView.text = stringBuilder.trimEnd()
  }
}