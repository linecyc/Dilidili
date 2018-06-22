package com.linecy.module.core.mvp

import android.os.Bundle
import com.linecy.module.core.utils.Preconditions

/**
 * presenter的静态代理，以便控制presenter.
 *
 * @author by linecy.
 */
class RxPresenterDelegate<T : BaseView>(private var presenter: Presenter<T>) : Presenter<T> {


  fun delegate(presenter: Presenter<T>) {
    this.presenter = Preconditions.checkNotNull(presenter)
  }

  override fun attach(view: T) {
    presenter.attach(view)
  }

  override fun restoreInstanceState(savedInstanceState: Bundle?) {
    presenter.restoreInstanceState(savedInstanceState)
  }

  override fun saveInstanceState(outState: Bundle) {
    presenter.saveInstanceState(outState)
  }

  override fun detach() {
    presenter.detach()
  }

}