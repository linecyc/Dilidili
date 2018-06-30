package com.linecy.dilidili.data.presenter.play

import com.linecy.dilidili.data.datasource.repository.CartoonRepository
import com.linecy.dilidili.data.model.Cartoon
import com.linecy.dilidili.data.model.PlayDetail
import com.linecy.module.core.mvp.RxPresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.regex.Pattern
import javax.inject.Inject

/**
 * @author by linecy.
 */
class PlayPresenter @Inject constructor(
    private val cartoonRepository: CartoonRepository) : RxPresenter<PlayView>() {

  /**
   *获取单集数据
   */
  fun getCartoon(cartoon: String) {


    //获取线路
    //val script = body.getElementsByTag("script")[5]
    //script.data().toString().split("var")

    // 如果用线路几+sourceUrl的形式，会导致ijkplayer抛moov atom not found 异常，故舍弃，
    //直接用iframe的src里?url后面的或者sourceUrl
//    when {
//      it.contains("line =") -> {
//
//      }
//      it.contains("sourceLib =") -> {
//
//      }
//      it.contains("sourceUrl =") -> {
//
//      }
//    }
    if (isLoading) {
      return
    }
    isLoading = true

    baseView?.showLoading()
    disposables.add(cartoonRepository.getCartoon(cartoon).map {

      val body = it.getElementsByTag("body")[0]
      val iframe = body.getElementsByTag("iframe")
      val detailUrl = body.select("#link h2 a").last().attr("href")
      var play: String? = null
      if (null != iframe && iframe.size > 0) {
        val list = iframe[0].attr("src").split("?url=")
        //如果切分不出数据就解析script里面的playerSwitch方法，拿到切换线路加载的url
        if (list.size > 1) {
          play = list[1]
        } else {
          val script = body.getElementsByTag("script")[5].data()
          val pattern = Pattern.compile("(?<=line\\[lineIndex] \\+ \")(.*)(?=\"\\))")
          val url = pattern.matcher(script)
          if (url.find()) {
            play = url.group()
          }
        }
      }
      val playDetail = body.selectFirst("div.play_detail")
      val cover = playDetail.selectFirst("div.player_img img").attr("src")
      val intro2 = playDetail.getElementById("intro2")
      val title = intro2.getElementsByTag("h1")[0].text()
      val curr = intro2.getElementsByTag("span")[0].text()
      val p = intro2.getElementsByTag("p")

      val aList = it.select("div.aside_cen2 div.num a")//集数列表
      val list = ArrayList<Cartoon>()
      aList.forEach {
        list.add(
            Cartoon(currentTitle = it.attr("title"), latest = it.text(),
                playDetail = it.attr("href")))
      }
      PlayDetail(title = title, playUrl = play, coverUrl = cover, current = curr,
          updateTime = p[p.size - 1].text().substring(4), detail = detailUrl, cartoonList = list)
    }.subscribeOn(Schedulers.io())
        .observeOn(
            AndroidSchedulers.mainThread()).subscribe({
          baseView?.showCartoonDetail(it)
        }, {
          isLoading = false
          baseView?.hideLoading()
          baseView?.showError(it.message)
          Timber.e("Error------>>:$it")

        }, {
          isLoading = false
          baseView?.hideLoading()
        }))
  }

  /**
   *获取每周数据列表
   */
  fun getWeekCartoon() {

    disposables.add(cartoonRepository.getWeekCartoon().flatMap {
      val week = it.select("div.change div.sldr ul.wrp")[0].select("div.book")
      val list = ArrayList<ArrayList<Cartoon>>(week.size)
      Timber.i("week-------->>${week.size}")
      for (i in 0 until week.size) {
        val arr = ArrayList<Cartoon>()
        val ele = week[i].select("a")
        Timber.i(ele.toString())
        ele.forEach {
          val p = it.select("figcaption p")

          if (p.size > 1) {
            arr.add(
                Cartoon(title = it.attr("title"), coverUrl = it.selectFirst("img").attr("src"),
                    latest = p[1].text(), currentTitle = p[0].text(), playDetail = it.attr("href")))
          } else {
            arr.add(
                Cartoon(title = it.attr("title"), coverUrl = it.selectFirst("img").attr("src"),
                    latest = "", currentTitle = p[0].text(), playDetail = it.attr("href")))
          }
        }
        list.add(arr)
      }
      Observable.just(list)
    }.subscribeOn(Schedulers.io())
        .observeOn(
            AndroidSchedulers.mainThread()).subscribe({
          Timber.e("Next------>>:$it")
        }, {
          baseView?.hideLoading()

          Timber.e("Error------>>:$it")

        }, {
          baseView?.hideLoading()
        }))
  }

}
