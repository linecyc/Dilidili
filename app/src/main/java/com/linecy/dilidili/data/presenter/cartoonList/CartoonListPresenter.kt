package com.linecy.dilidili.data.presenter.cartoonList

import android.text.TextUtils
import com.linecy.dilidili.data.datasource.repository.CartoonRepository
import com.linecy.dilidili.data.model.Label
import com.linecy.dilidili.data.model.Serials
import com.linecy.module.core.mvp.RxPresenter
import com.linecy.module.core.rx.observeOnUi
import com.linecy.module.core.rx.subscribeBy
import timber.log.Timber
import javax.inject.Inject

/**
 * @author by linecy.
 */

class CartoonListPresenter @Inject constructor(private val
cartoonRepository: CartoonRepository) : RxPresenter<CartoonListView>() {


  fun getCartoonList(link: String) {
    baseView?.showLoading()
    disposables.add(cartoonRepository.getCartoonList(link).map {
      val body = it.body()
      val dl = body.select("div.anime_list dl")
      val list = ArrayList<Serials>()
      dl.forEach {
        val cover = it.selectFirst("dt img").attr("src")
        val a = it.selectFirst("h3 a")
        val title = a.text()
        val url = a.attr("href")
        val divLabel = it.select("div.d_label a")
        val labels = ArrayList<Label>()
        var location: Label? = null
        var year: Label? = null

        divLabel.forEachIndexed { index, element ->
          when (index) {
            0 -> {
              location = Label(element.text(), element.attr("href"))
            }
            1 -> {
              if (!TextUtils.isEmpty(element.attr("title"))) {
                year = Label(element.text(), element.attr("href"))
              } else {
                val text = it.select("div.d_label")[1].text()
                if (!TextUtils.isEmpty(text) && text.contains("年代")) {
                  year = Label(text.substring(3), null)
                }
                labels.add(Label(element.text(), element.attr("href")))
              }
            }
            else -> labels.add(Label(element.text(), element.attr("href")))
          }
        }
        val p = it.select("p")?.filter {
          !TextUtils.isEmpty(it.text())
        }
        var watchingFocus: String? = null
        var detail: String? = null
        var state: String? = null
        //p过滤掉null后依次看点、声优、简介、状态
        if (p != null && p.size == 4) {
          if (!TextUtils.isEmpty(p[0].text())) {
            watchingFocus = p[0].text()
          }
          if (!TextUtils.isEmpty(p[2].text())) {
            detail = p[2].text()
          }
          if (!TextUtils.isEmpty(p[3].text())) {
            state = p[3].text()
          }
        }
        list.add(
            Serials(title = title, coverUrl = cover, detail = detail, link = url,
                location = location, year = year, labels = labels, watchingFocus = watchingFocus,
                state = state))
      }

      list
    }.observeOnUi().subscribeBy({
      baseView?.hideLoading()
      baseView?.showCartoonList(it)
    }, {
      baseView?.hideLoading()
      baseView?.showError()
      Timber.e(it)
    }))
  }
}