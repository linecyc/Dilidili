package com.linecy.dilidili.ui.misc

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.linecy.dilidili.R
import kotlinx.android.synthetic.main.layout_empty.view.empty
import kotlinx.android.synthetic.main.layout_error.view.error

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
    error.visibility = View.GONE
    empty.visibility = View.GONE
  }

  override fun setDisplayedChildId(id: Int) {
    super.setDisplayedChildId(id)
    when (id) {
      R.id.error -> {
        error.visibility = View.VISIBLE
        this.error.setOnClickListener {
          onReloadCallBack?.onReload()
          error.visibility = View.GONE
        }
      }
      R.id.empty -> {
        empty.visibility = View.VISIBLE
        this.empty.setOnClickListener {
          onEmptyCallback?.onEmpty()
          empty.visibility = View.GONE
        }
      }
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