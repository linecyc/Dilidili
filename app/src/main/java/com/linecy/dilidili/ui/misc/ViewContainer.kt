package com.linecy.dilidili.ui.misc

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.linecy.dilidili.R

/**
 * @author by linecy.
 */
class ViewContainer(context: Context, attrs: AttributeSet?) : BatterViewAnimator(
    context, attrs) {

  //空页面数据
  val emptyMessage = lazyInitView<TextView>(R.id.emptyMessage)
  val emptyImage = lazyInitView<ImageView>(R.id.emptyImage)

  //错误页数据
  val errorMessage = lazyInitView<TextView>(R.id.errorMessage)
  val errorImage = lazyInitView<ImageView>(R.id.errorImage)


  init {
    View.inflate(context, R.layout.view_container, this)
  }

  private var onReloadCallBack: OnReloadCallBack? = null
  private var onEmptyCallback: OnEmptyCallback? = null


  override fun onFinishInflate() {
    super.onFinishInflate()
    findViewById<ConstraintLayout>(R.id.error).setOnClickListener { onReloadCallBack?.onReload() }
    findViewById<ConstraintLayout>(R.id.empty).setOnClickListener { onEmptyCallback?.onEmpty() }
  }

  /**
   * 懒加载
   */
  private fun <V : View> lazyInitView(id: Int): Lazy<V> = lazy { findViewById<V>(id) }

  fun setOnReloadCallBack(onReloadCallBack: OnReloadCallBack) {
    this.onReloadCallBack = onReloadCallBack
  }

  fun setOnEmptyCallback(onEmptyCallback: OnEmptyCallback) {
    this.onEmptyCallback = onEmptyCallback
  }

  interface OnReloadCallBack {
    fun onReload()
  }

  interface OnEmptyCallback {
    fun onEmpty()
  }

}