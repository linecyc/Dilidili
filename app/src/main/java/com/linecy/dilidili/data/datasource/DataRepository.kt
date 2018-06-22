package com.linecy.dilidili.data.datasource

import android.os.Handler
import android.os.Message

/**
 *
 *
 * @author by linecy.
 */
open class DataRepository(protected var callback: LoadCallback?) {
  protected var handler: Handler? = null

  init {
    handler = LoadHandler()
  }

  interface LoadCallback {

    //0
    fun onStart()

    //1
    fun onCompleted(data: Any?)

    //-1
    fun onError(e: String?)
  }

  inner class LoadHandler : Handler() {

    override fun handleMessage(msg: Message?) {
      if (null != msg && callback != null) {
        when (msg.what) {
          0 -> {
            callback?.onStart()
          }
          1 -> {
            callback?.onCompleted(msg.obj)
          }
          -1 -> {
            callback?.onError(msg.obj.toString())

          }
        }
      }
    }
  }
}