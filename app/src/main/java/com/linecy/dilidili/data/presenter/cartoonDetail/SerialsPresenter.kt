package com.linecy.dilidili.data.presenter.cartoonDetail

import android.text.TextUtils
import com.linecy.dilidili.data.datasource.repository.CartoonRepository
import com.linecy.dilidili.data.model.Cartoon
import com.linecy.dilidili.data.model.Label
import com.linecy.dilidili.data.model.Serials
import com.linecy.dilidili.data.model.SerialsDetail
import com.linecy.module.core.mvp.RxPresenter
import com.linecy.module.core.rx.observeOnUi
import com.linecy.module.core.rx.subscribeBy
import io.reactivex.Observable
import org.jsoup.nodes.Element
import javax.inject.Inject

/**
 * @author by linecy.
 */

class SerialsPresenter @Inject constructor(private val
cartoonRepository: CartoonRepository) : RxPresenter<SerialsView>() {

  fun getSerials(link: String) {
    baseView?.showLoading()
    disposables.add(cartoonRepository.getSerials(link).flatMap {
      val body = it.body()
      //拿到当前的serials
      val serialsList = ArrayList<Serials>()
      serialsList.add(dealSerials(body))
      //拿到当前的推荐2
      val recommend2 = dealRecommend(body)

      //过滤当前番剧有多少季及其链接
      val seasons = ArrayList<Observable<Serials>>()
      body.select("div.stitle span.pk_time a").forEach {
        val href = it.attr("href")
        if (!TextUtils.isEmpty(href) && !href.contains("javascript")) {
          seasons.add(getOtherSerials(it.text(), href))
        }
      }
      if (seasons.size > 0) {
        Observable.zipIterable<Serials, SerialsDetail>(seasons, {
          it.forEach {
            serialsList.add(it as Serials)
          }
          SerialsDetail(serialsList, null, recommend2)
        }, true, 1)
      } else {
        Observable.just(SerialsDetail(serialsList, null, recommend2))
      }
    }.observeOnUi()
        .subscribeBy({
          baseView?.hideLoading()
          baseView?.showSerialsDetail(it)
        }, {
          baseView?.hideLoading()
          baseView?.showError()
        }))
  }

  /**
   * 遍历番剧其他季的第一集的链接
   */
  private fun getOtherSerials(name: String, link: String): Observable<Serials> {
    return cartoonRepository.getSerials(link).map {
      val body = it.body()
      val con = body.selectFirst("div.time_con div.swiper-wrapper div.swiper-slide li.fj_li")

      //如果想得到集合就foreach吧
      con?.let {
        val titles = it.selectFirst("div").text().split(" ")
        val ca = Cartoon(title = it.select("span")[0].text(),
            coverUrl = it.select("img")[0].attr("src"),
            currentTitle = titles[titles.size - 1],
            playDetail = it.select("a")[0].attr("href"))
        Serials(season = name, cartoons = listOf(ca))
      } ?: Serials(season = name, cartoons = null)
    }
  }

  /**
   * 处理推荐
   */
  private fun dealRecommend(body: Element): ArrayList<Cartoon> {
    //过滤推荐
    //只有文字和链接数据
    val recommend1 = ArrayList<Label>()
    val sa = body.select("div.stitle span.s_f a")
    sa?.forEach {
      recommend1.add(Label(it.text(), it.attr("href")))
    }
    //包含文字、链接、封面数据
    val recommend2 = ArrayList<Cartoon>()
    val ma = body.select("div.swiper-slide ul.m_pic a")
    ma?.forEach {
      recommend2.add(Cartoon(title = it.selectFirst("p").text(),
          coverUrl = it.selectFirst("img").attr("src"), playDetail = ma.attr("href")))
    }
    return recommend2
  }

  /**
   * 处理剧集
   */
  private fun dealSerials(body: Element): Serials {
    val detail = body.selectFirst("div.aside_cen2 div.detail")
    val con = body.select("div.time_con div.swiper-wrapper div.swiper-slide li.fj_li")
    val dd = detail.selectFirst("dd")
    val title = dd.selectFirst("h1").text()
    val header = dd.selectFirst("a.detail_sc")
    val url = header.attr("data-url")
    val cover = header.attr("data-icon")
    val labels = ArrayList<Label>()
    val labels1 = dd.select("div.d_label")
    val labels2 = dd.select("div.d_label2")

    //过滤各式各样的标签
    var location: Label? = null
    var year: Label? = null
    labels1.select("a").forEachIndexed { index, element ->
      when (index) {
        0 -> {
          location = Label(element.text(), element.attr("href"))
        }
        1 -> {
          if (!TextUtils.isEmpty(element.attr("title"))) {
            year = Label(element.text(), element.attr("href"))
          } else {
            val text = dd.select("div.d_label")[1].text()
            if (!TextUtils.isEmpty(text) && text.contains("年代")) {
              year = Label(text.substring(3), null)
            }
            labels.add(Label(element.text(), element.attr("href")))
          }
        }
        else -> labels.add(Label(element.text(), element.attr("href")))
      }
    }
    val state: String? = labels1.last().text()
    var watchingFocus: String? = null
    var cartoonDetail: String? = null
    if (labels2.size == 4) {
      watchingFocus = labels2[0].text()
      cartoonDetail = labels2[2].text()
    }

    val cartoons = ArrayList<Cartoon>()
    con?.forEach {
      val titles = it.selectFirst("div").text().split(" ")
      cartoons.add(Cartoon(title = it.select("span")[0].text(),
          coverUrl = it.select("img")[0].attr("src"), currentTitle = titles[titles.size - 1],
          playDetail = it.select("a")[0].attr("href")))
    }
    return Serials(title = title, coverUrl = cover, detail = cartoonDetail, link = url,
        location = location, year = year, labels = labels, watchingFocus = watchingFocus,
        state = state,
        cartoons = cartoons)

  }

}