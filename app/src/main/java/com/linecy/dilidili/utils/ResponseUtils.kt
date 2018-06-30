package com.linecy.dilidili.utils

import android.text.TextUtils
import io.reactivex.Observable
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
    return Observable.create { o ->
      try {
        val document = Jsoup.connect(url).timeout(10000).get()
        o.onNext(document)
        o.onComplete()
      } catch (e: IOException) {
        o.onError(e)
      }
    }
  }
}