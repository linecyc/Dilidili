package com.linecy.dilidili.data.presenter.cartoonWeek

import android.text.TextUtils
import com.linecy.dilidili.data.datasource.repository.CartoonRepository
import com.linecy.dilidili.data.model.Cartoon
import com.linecy.dilidili.data.service.DiliApi
import com.linecy.module.core.mvp.RxPresenter
import com.linecy.module.core.rx.subscribeBy
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * @author by linecy.
 */
class CartoonWeekPresenter @Inject constructor(
    private val cartoonRepository: CartoonRepository) : RxPresenter<CartoonWeekView>() {

  /**
   *获取每周数据列表
   */
  fun getWeekCartoon() {
    baseView?.showLoading()
    disposables.add(cartoonRepository.getWeekCartoon().flatMap {
      val week = it.select("div.change div.sldr ul.wrp")[0].select("div.book")
      val list = ArrayList<ArrayList<Cartoon>>(week.size)
      for (i in 0 until week.size) {
        val arr = ArrayList<Cartoon>()
        val ele = week[i].select("a")
        ele.forEach {
          val p = it.select("figcaption p")
          var cover = it.selectFirst("img").attr("src")
          if (!TextUtils.isEmpty(cover)) {
            if (!cover.startsWith("http://")) {
              cover = DiliApi.baseUrl + cover
            }
          }
          if (p.size > 1) {
            arr.add(
                Cartoon(title = it.attr("title"), coverUrl = cover,
                    latest = p[1].text(), currentTitle = p[0].text(), playDetail = it.attr("href")))
          } else {
            arr.add(
                Cartoon(title = it.attr("title"), coverUrl = cover,
                    latest = "", currentTitle = p[0].text(), playDetail = it.attr("href")))
          }
        }
        list.add(arr)
      }
      Observable.just(list)
    }.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeBy({
          baseView?.hideLoading()
          baseView?.showWeekList(it)
        }, {
          baseView?.hideLoading()
          baseView?.showError()
        }))
  }
}