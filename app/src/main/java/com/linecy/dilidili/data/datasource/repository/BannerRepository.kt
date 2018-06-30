package com.linecy.dilidili.data.datasource.repository

import io.reactivex.Observable
import org.jsoup.nodes.Document

/**
 * @author by linecy.
 */
interface BannerRepository {

  fun getBanners(): Observable<Document>

}