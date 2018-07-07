package com.linecy.dilidili.ui.misc

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.linecy.dilidili.R

/**
 * 空数据、错误、正常三页面切换控制器。
 * 增加一个layout_null页面用于初始化展示，优化UI
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
    findViewById<ConstraintLayout>(R.id.error).setOnClickListener {
      setDisplayedChildId(R.id.layoutNull)
      onReloadCallBack?.onReload()
    }
    findViewById<ConstraintLayout>(R.id.empty).setOnClickListener {
      setDisplayedChildId(R.id.layoutNull)
      onEmptyCallback?.onEmpty()
    }
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