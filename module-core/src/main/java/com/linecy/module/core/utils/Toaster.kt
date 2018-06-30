package com.linecy.module.core.utils

import android.app.Application
import android.os.Looper
import android.support.annotation.StringRes
import android.widget.Toast

/**
 * @author by linecy.
 */
class Toaster(private val application: Application) {

  private var preToast: Toast? = null

  fun showText(text: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
    show(text, duration)
  }

  fun showText(@StringRes stringResId: Int, duration: Int = Toast.LENGTH_SHORT) {
    showText(application.getString(stringResId), duration)
  }

  private fun show(text: CharSequence, duration: Int) {
    if (assertMainThread()) {
      Looper.prepare()
    }

    val toast = Toast.makeText(application, text, duration)
    clear()
    toast.show()

    if (assertMainThread()) {
      Looper.loop()
    }

    preToast = toast
  }

  fun clear() {
    preToast?.cancel()
  }

  private fun assertMainThread(): Boolean {
    return Thread.currentThread() !== Looper.getMainLooper().thread
  }

}