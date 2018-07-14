package com.linecy.dilidili.data.datasource

import com.linecy.dilidili.data.datasource.repository.CartoonRepository
import com.linecy.dilidili.data.service.DiliApi
import com.linecy.dilidili.utils.ResponseUtils
import io.reactivex.Observable
import org.jsoup.nodes.Document
import javax.inject.Inject

/**
 *
 * @author by linecy.
 */
class CartoonDataSource @Inject internal constructor() : CartoonRepository {


  override fun getCartoon(cartoonUrl: String): Observable<Document> {
    return ResponseUtils.getDataWithUrl(cartoonUrl)
  }

  override fun getLatestUpdate(): Observable<Document> {
    return ResponseUtils.getDataWithUrl(DiliApi.latestUpdateUrl)
  }

  override fun getWeekCartoon(): Observable<Document> {
    return ResponseUtils.getDataWithUrl(DiliApi.baseUrl)
  }

  override fun getSerials(serialsUrl: String): Observable<Document> {
    return ResponseUtils.getDataWithUrl(
        if (serialsUrl.startsWith("http:")) serialsUrl else DiliApi.baseUrl + serialsUrl)
  }

  override fun getCartoonList(link: String): Observable<Document> {
    return ResponseUtils.getDataWithUrl(
        if (link.startsWith("http:")) link else DiliApi.baseUrl + link)
  }

}