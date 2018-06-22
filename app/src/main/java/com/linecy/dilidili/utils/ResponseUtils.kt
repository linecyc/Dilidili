package com.linecy.dilidili.utils

import android.text.TextUtils
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.IOException

/**
 * @author by linecy.
 */
object ResponseUtils {


  fun getDataWithUrl(url: String): Observable<Document> {
    if (TextUtils.isEmpty(url)) {
      throw NullPointerException("api url must not be null")
    }
    return Observable.create(ObservableOnSubscribe<Document> { o ->
      try {
        val document = Jsoup.connect(url).timeout(10000).get()
        o.onNext(document)
        o.onComplete()
      } catch (e: IOException) {
        e.printStackTrace()
        o.onError(e)
      }
    })
  }
}