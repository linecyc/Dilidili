package com.linecy.dilidili.data.datasource

import com.linecy.dilidili.data.datasource.repository.BannerRepository
import com.linecy.dilidili.data.service.DiliApi
import com.linecy.dilidili.utils.ResponseUtils
import io.reactivex.Observable
import org.jsoup.nodes.Document
import javax.inject.Inject

/**
 * @author by linecy.
 */
class BannerDataSource @Inject internal constructor() : BannerRepository {

  /**
   * 获取轮播图
   */
  override fun getBanners(): Observable<Document> {
    return ResponseUtils.getDataWithUrl(DiliApi.baseUrl)

  }

}