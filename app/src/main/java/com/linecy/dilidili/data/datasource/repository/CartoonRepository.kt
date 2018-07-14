package com.linecy.dilidili.data.datasource.repository

import io.reactivex.Observable
import org.jsoup.nodes.Document

/**
 * @author by linecy.
 */
interface CartoonRepository {

  /**
   * 获取当前番剧播放相关
   *
   * @param cartoonUrl 当前番剧
   */
  fun getCartoon(cartoonUrl: String): Observable<Document>

  /**
   * 获取最近更新番剧列表
   *
   */
  fun getLatestUpdate(): Observable<Document>

  /**
   * 获取每周番剧列表
   */
  fun getWeekCartoon(): Observable<Document>

  /**
   * 获取当前番剧详情页
   *
   * @param serialsUrl 哪一个番剧
   */
  fun getSerials(serialsUrl: String): Observable<Document>

  /**
   * 获取当前番剧的详情页
   */
  fun getCartoonList(link: String): Observable<Document>

}