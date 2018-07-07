package com.linecy.dilidili.ui.misc

import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.FloatingActionButton.OnVisibilityChangedListener
import android.support.v4.view.ViewCompat
import android.util.AttributeSet
import android.view.View


/**
 *滚动到顶部
 * @author by linecy.
 */

class ScrollTopBehavior(context: Context,
    attributeSet: AttributeSet) : FloatingActionButton.Behavior(context, attributeSet) {


  override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout,
      child: FloatingActionButton, directTargetChild: View, target: View, axes: Int,
      type: Int): Boolean {
    return axes == ViewCompat.SCROLL_AXIS_VERTICAL
  }

  override fun onNestedScroll(coordinatorLayout: CoordinatorLayout, child: FloatingActionButton,
      target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int,
      type: Int) {
//    if (dyConsumed > 0 && dyUnconsumed == 0) {
//      Timber.i("上滑中。。。")
//    }
//    if (dyConsumed == 0 && dyUnconsumed > 0) {
//      Timber.i("到边界了还在上滑。。。")
//    }
//    if (dyConsumed < 0 && dyUnconsumed == 0) {
//      Timber.i("下滑中。。。")
//    }
//    if (dyConsumed == 0 && dyUnconsumed < 0) {
//      Timber.i("到边界了，还在下滑。。。")
//    }

    if (dyConsumed > 0 && dyUnconsumed == 0 && child.visibility != View.VISIBLE) {// 显示
      child.isEnabled = true
      child.show()

    } else if (dyConsumed < 0 && dyUnconsumed == 0 && child.visibility == View.VISIBLE) {//隐藏
      child.hide(object : OnVisibilityChangedListener() {
        override fun onHidden(fab: FloatingActionButton?) {
          super.onHidden(fab)
          fab?.visibility = View.INVISIBLE
        }
      })
    }
  }
}