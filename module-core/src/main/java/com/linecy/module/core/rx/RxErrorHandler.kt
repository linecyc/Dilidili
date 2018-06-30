package com.linecy.module.core.rx

import com.linecy.module.core.utils.Toaster
import io.reactivex.exceptions.UndeliverableException
import io.reactivex.functions.Consumer
import io.reactivex.plugins.RxJavaPlugins
import timber.log.Timber
import java.io.IOException

/**
 * 默认的error handler.
 * @author by linecy.
 */

class RxErrorHandler(private val toaster: Toaster, private val rxBus: RxBus?,
    private val backPressureRxBus: BackPressureRxBus?) : Consumer<Throwable> {

  companion object {
    fun install(toaster: Toaster, rxBus: RxBus? = null,
        backPressureRxBus: BackPressureRxBus? = null) {
      RxJavaPlugins.setErrorHandler(RxErrorHandler(toaster, rxBus, backPressureRxBus))
    }
  }

  override fun accept(t: Throwable?) {
    if (t != null) {
      if (t is UndeliverableException) {
        toaster.showText("UnknownHostException")
      } else if (t is IOException) {

      }
      Timber.e("RxErrorHandler:$t")
    }
  }


}