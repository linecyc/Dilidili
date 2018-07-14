package com.linecy.dilidili.data.datasource

import com.linecy.dilidili.data.datasource.repository.SearchRepository
import com.linecy.dilidili.utils.ResponseUtils
import io.reactivex.Observable
import org.jsoup.nodes.Document
import javax.inject.Inject

/**
 * @author by linecy.
 */
class SearchDataSource @Inject constructor() : SearchRepository {

  override fun search(content: String): Observable<Document> {
    val url = "http://zhannei.baidu.com/cse/search?s=4514337681231489739&loc=http://www.dilidili.wang/search/index.html?q=$content&button=%E6%90%9C%E7%B4%A2&ie=utf8&site=www.dilidili.wang&width=552&q=$content&button=%E6%90%9C%E7%B4%A2&ie=utf8&site=www.dilidili.wang&wt=1&ht=1&pn=10&fpos=2&rmem=0&reg="
    return ResponseUtils.getDataWithUrl(url)
  }

  override fun loadMoreResult(content: String, next: Int): Observable<Document> {
    val url = "http://zhannei.baidu.com/cse/search?q=$content&p=$next&s=4514337681231489739&wt=1&ht=1&pn=10"
    return ResponseUtils.getDataWithUrl(url)
  }

}