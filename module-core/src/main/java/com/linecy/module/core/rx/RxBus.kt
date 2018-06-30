package com.linecy.module.core.rx

import android.support.annotation.NonNull
import com.linecy.module.core.utils.Preconditions
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject

/**
 * 事件总线。
 * @author by linecy.
 */
class RxBus {
  private val subject: Subject<Any>

  init {
    subject = PublishSubject.create()
  }


  fun post(@NonNull event: Any) {
    Preconditions.checkNotNull(event, "event == null")
    subject.onNext(event)
  }

  fun <T> toObservable(@NonNull eventType: Class<T>): Observable<T> {

    Preconditions.checkNotNull(eventType, "eventType == null")
    return subject.ofType(eventType)
  }

  fun unsubscribe() {
    subject.onComplete()
  }


}