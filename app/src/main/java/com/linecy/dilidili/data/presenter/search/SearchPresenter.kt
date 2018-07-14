package com.linecy.dilidili.data.presenter.search

import android.text.TextUtils
import com.linecy.dilidili.data.datasource.repository.SearchRepository
import com.linecy.dilidili.data.model.ResultItem
import com.linecy.dilidili.data.model.SearchResult
import com.linecy.module.core.mvp.RxPresenter
import com.linecy.module.core.rx.subscribeBy
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jsoup.nodes.Document
import java.util.regex.Pattern
import javax.inject.Inject

/**
 * @author by linecy.
 */
class SearchPresenter @Inject constructor(
    private val searchRepository: SearchRepository) : RxPresenter<SearchView>() {

  private lateinit var content: String
  /**
   * 搜索
   */
  fun search(content: String) {
    this.content = content
    baseView?.showLoading()
    disposables.add(
        observableResult(searchRepository.search(content), 0).subscribeOn(Schedulers.io())
            .observeOn(
                AndroidSchedulers.mainThread()).subscribeBy({
              baseView?.hideLoading()
              baseView?.showResult(it)
            }, {
              baseView?.hideLoading()
              baseView?.showError()
            }))
  }


  fun loadMore(next: Int) {
    if (isLoading) {
      return
    }
    isLoading = true
    disposables.add(
        observableResult(searchRepository.loadMoreResult(this.content, next), next).subscribeOn(
            Schedulers.io())
            .observeOn(
                AndroidSchedulers.mainThread()).subscribeBy({
              baseView?.hideLoading()
              baseView?.showMore(it)
              isLoading = false
            }, {
              baseView?.hideLoading()
              isLoading = false
            }))
  }

  private fun observableResult(observable: Observable<Document>,
      current: Int): Observable<SearchResult> {
    return observable.map {
      val result = it.select("#results")
      val resultCount = result.select("span.support-text-top").text()

      val div = result.select("div.result")
      val list = ArrayList<ResultItem>(div.size)
      val p = Pattern.compile("\\d+")
      val m = p.matcher(resultCount)
      var total = 0
      if (m.find()) {
        total = if (0 == m.group().toInt() % 10) {
          m.group().toInt() / div.size
        } else {
          m.group().toInt() / div.size + 1
        }
      }
      div.forEach {
        val a = it.select("h3.c-title a")
        val link = a[0].attr("href")
        val title = a[0].text().substringBefore("- 在线").substringBefore("-在线")//过滤掉在线下载等内容
        val detail = it.select("div.c-abstract").text()
        val img = it.select("img")
        var cover = ""
        if (img != null && img.size > 0) {
          cover = img[0].attr("src")
        }

        //过滤掉无关内容,可能导致当前页过滤后无数据
        if (!TextUtils.isEmpty(title) && !title.startsWith("嘀哩嘀哩")) {
          list.add(ResultItem(title = title, coverUrl = cover, detail = detail, link = link))
        }
      }
      SearchResult(resultCount = resultCount, list = list, currentPage = current,
          totalPage = total)
    }
  }
}