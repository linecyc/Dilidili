package com.linecy.dilidili.ui.misc

import android.content.Context
import android.util.AttributeSet
import android.widget.ViewAnimator

/**
 * @author by linecy.
 */
abstract class BatterViewAnimator(context: Context, attrs: AttributeSet?) : ViewAnimator(
    context, attrs) {


  fun setDisplayedChildId(id: Int) {
    if (getDisplayedChildId() == id) {
      return
    }

    val count = childCount
    for (i in 0 until count) {
      if (getChildAt(i).id == id) {
        displayedChild = i
        return
      }

    }
    val name = resources.getResourceEntryName(id)
    throw IllegalArgumentException("No view with ID $name")
  }

  fun getDisplayedChildId(): Int {
    return getChildAt(displayedChild).id
  }
}