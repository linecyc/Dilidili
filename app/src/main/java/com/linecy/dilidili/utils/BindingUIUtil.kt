package com.linecy.dilidili.utils

import android.databinding.BindingAdapter
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.text.SpannableString
import android.text.Spanned
import android.widget.ImageView
import android.widget.TextView
import com.linecy.dilidili.R
import com.linecy.dilidili.data.model.Cartoon
import com.linecy.dilidili.di.module.GlideApp
import com.linecy.dilidili.ui.home.adapter.CartoonAdapter
import com.linecy.dilidili.ui.misc.GlideRoundTransform
import com.linecy.dilidili.ui.misc.LabelBackgroundSpan


/**
 *
 * @author by linecy.
 */
object BindingUIUtil {


  @JvmStatic
  @BindingAdapter("cartoonAdapter")
  fun setItems(listView: RecyclerView, cartoons: List<Cartoon>?) {
    val adapter = listView.adapter as CartoonAdapter?
    adapter?.refreshData(cartoons)
  }


  @JvmStatic
  @BindingAdapter("loadPicture")
  fun setPicture(imageView: ImageView, url: String?) {
    url?.let {
      GlideApp.with(imageView.context)
          .load(url)
          .transform(GlideRoundTransform())
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
}