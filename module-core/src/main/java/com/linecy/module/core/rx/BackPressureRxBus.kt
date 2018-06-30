package com.linecy.module.core.rx

import android.support.annotation.NonNull
import com.linecy.module.core.utils.Preconditions
import io.reactivex.Flowable
import io.reactivex.processors.FlowableProcessor
import io.reactivex.processors.PublishProcessor

/**
 * 背压事件总线。
 * @author by linecy.
 */
class BackPressureRxBus {
  private val subject: FlowableProcessor<Any>

  init {
    subject = PublishProcessor.create()
  }


  fun post(@NonNull event: Any) {
    Preconditions.checkNotNull(event, "event == null")
    subject.onNext(event)
  }

  fun <T> toObservable(@NonNull eventType: Class<T>): Flowable<T> {
    Preconditions.checkNotNull(eventType, "eventType == null")
    return subject.ofType(eventType)
  }

  fun unsubscribe() {
    subject.onComplete()
  }

}