package com.linecy.module.core.rx;

import org.reactivestreams.Subscriber;

/**
 * @author linecy
 */
public abstract class EndlessSubscriber<T> implements Subscriber<T> {
  @Override public void onComplete() {
  }

  @Override public void onError(Throwable e) {
  }
}
