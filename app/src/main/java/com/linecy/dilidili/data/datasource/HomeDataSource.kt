package com.linecy.dilidili.data.datasource

import com.linecy.dilidili.data.service.DiliApi
import com.linecy.dilidili.utils.ResponseUtils
import io.reactivex.Observable
import org.jsoup.nodes.Document
import javax.inject.Inject

/**
 * @author by linecy.
 */
class HomeDataSource @Inject internal constructor() : HomeRepository {
  override fun getBanners(): Observable<Document> {
    return ResponseUtils.getDataWithUrl(DiliApi.baseUrl)

  }

}