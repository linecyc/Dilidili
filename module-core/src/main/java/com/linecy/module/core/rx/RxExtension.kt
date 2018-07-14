package com.linecy.module.core.rx

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers

/**
 * Extensions for RxJava2.
 * @author by linecy.
 */

private val onNextStub: (Any) -> Unit = { }
private val onCompleteStub: () -> Unit = { }
private val onErrorStub: (Throwable) -> Unit = { }

/**
 * Wrap subscribeBy, add onComplete in onError.
 */
fun <T : Any> Observable<T>.subscribeBy(
    onNext: (T) -> Unit = onNextStub,
    onError: (Throwable) -> Unit = onErrorStub,
    onComplete: () -> Unit = onCompleteStub

): Disposable {
  val onEnd: (Throwable) -> Unit = {
    onError(it)
    onComplete()
    RxJavaPlugins.onError(it)
  }
  return subscribe(onNext, onEnd, onComplete)
}

/**
 * Observe on UI thread, this will update UI.
 */
fun <T> Observable<T>.observeOnUi(): Observable<T> = this.subscribeOn(Schedulers.io()).observeOn(
    AndroidSchedulers.mainThread())

/**
 * Observe on IO thread, this usually used for background task.
 */
fun <T> Observable<T>.observeOnIo(): Observable<T> = this.subscribeOn(Schedulers.io()).observeOn(
    Schedulers.io())

/**
 * Subscribe silently without update UI.
 */
fun <T> Observable<T>.subscribeSilently(): Disposable = this.subscribeOn(Schedulers.io()).observeOn(
    Schedulers.io()).subscribe { }

/**
 * Add one [Disposable] to [CompositeDisposable].
 *
 * @param disposables [CompositeDisposable]
 */
fun Disposable.into(disposables: CompositeDisposable) = checkNotNull(disposables).add(this)