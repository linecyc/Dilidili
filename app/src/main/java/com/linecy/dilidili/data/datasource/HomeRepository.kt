package com.linecy.dilidili.data.datasource

import io.reactivex.Observable
import org.jsoup.nodes.Document

/**
 * @author by linecy.
 */
interface HomeRepository {

  fun getBanners(): Observable<Document>

}