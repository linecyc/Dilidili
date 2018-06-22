package com.linecy.module.core.mvp

import android.os.Bundle
import android.support.annotation.CallSuper
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * 基类presenter。
 *
 * @author by linecy.
 */
abstract class RxPresenter<T : BaseView> : Presenter<T> {

  protected val disposables = CompositeDisposable()
  protected var baseView: T? = null

  override fun restoreInstanceState(savedInstanceState: Bundle?) {

  }

  override fun saveInstanceState(outState: Bundle) {
  }

  @CallSuper
  override fun attach(view: T) {
    this.baseView = view
  }

  @CallSuper
  override fun detach() {
    disposables.clear()
    baseView = null
  }

  protected fun <T> observeOnView(observable: Observable<T>): Observable<T> {
    return observable.subscribeOn(Schedulers.io())
        .unsubscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
  }
}