package com.linecy.module.core.mvp

import android.os.Bundle

/**
 *
 * @author by linecy.
 */
interface Presenter<T : BaseView> {

  fun attach(view: T)

  fun restoreInstanceState(savedInstanceState: Bundle?)

  fun saveInstanceState(outState: Bundle)

  fun detach()

}