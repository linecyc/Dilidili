package com.linecy.dilidili.data.presenter.home

import android.text.TextUtils
import com.linecy.dilidili.data.datasource.repository.BannerRepository
import com.linecy.dilidili.data.datasource.repository.CartoonRepository
import com.linecy.dilidili.data.model.Banner
import com.linecy.dilidili.data.model.Cartoon
import com.linecy.dilidili.data.model.HomeData
import com.linecy.dilidili.data.service.DiliApi
import com.linecy.module.core.mvp.RxPresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.regex.Pattern
import javax.inject.Inject

/**
 * @author by linecy.
 */

class HomePresenter @Inject constructor(
    private val bannerRepository: BannerRepository,
    private val cartoonRepository: CartoonRepository) : RxPresenter<HomeView>() {

  /**
   * 加载轮播图和最近更新
   */
  fun getHomeData() {
    if (isLoading) {
      return
    }
    isLoading = true

    baseView?.showLoading()
    disposables.add(Observable.zip(loadBanner(), loadLatest(),
        BiFunction<List<Banner>, List<Cartoon>, HomeData> { banners, cartoons ->
          HomeData(banners, cartoons)
        }).subscribeOn(
        Schedulers.io())
        .observeOn(
            AndroidSchedulers.mainThread()).subscribe({
          isLoading = false
          baseView?.hideLoading()
          baseView?.showBannerList(it.banners)
          baseView?.showCartoonList(it.cartoons)

        }, {
          isLoading = false
          baseView?.hideLoading()
          baseView?.showError()
          Timber.e("Error------>>:$it")

        }))
  }


  /**
   *获取最近更新列表
   */
  fun getLatestUpdate() {
    disposables.add(loadLatest().subscribeOn(Schedulers.io())
        .observeOn(
            AndroidSchedulers.mainThread()).subscribe({
          Timber.e("latest------>>:$it")

        }, {
          isLoading = false
          baseView?.hideLoading()
          Timber.e("Error------>>:$it")

        }, {
          isLoading = false
          baseView?.hideLoading()
        }))
  }

  private fun loadBanner(): Observable<List<Banner>> {
    return bannerRepository.getBanners().map {
      it.select("#slider div.shade a")
    }.flatMap {
      val list = ArrayList<Banner>()
      it.forEach {
        val p = Pattern.compile("(?<=background: url\\()(.*)(?=\\) )")
        val m = p.matcher(it.attr("style"))

        //过滤掉广告、游戏等推广
        val link = it.attr("href")
        if (m.find() && m.group().startsWith(DiliApi.baseUrl) && !link.startsWith("http")) {
          list.add(Banner(m.group(), it.text(), DiliApi.baseUrl + link))
        }
      }
      Observable.just(list)
    }
  }


  private fun loadLatest(): Observable<List<Cartoon>> {
    return cartoonRepository.getLatestUpdate().map {
      //it.select("div.article")[0].select("a")//html首页最近更新
      it.select("div.article")[0].select("a")//最近更新页
    }.flatMap {
      val list = ArrayList<Cartoon>()
      it.forEach {
        val p = it.select("p")

        //过滤最新集数
        val regEx = "[^a-zA-Z0-9]"
        val numPattern = Pattern.compile(regEx)
        var num = numPattern.matcher(p[1].text()).replaceAll("")
        num = if (TextUtils.isEmpty(num)) {
          "已完结"
        } else {
          "第" + num + "话"
        }

        //取出封面背景
        val pattern = Pattern.compile("(?<=background: url\\()(.*)(?=\\))")
        val style = pattern.matcher(it.select("div.coverImg")[0].attr("style"))

        if (style.find()) {
          val a = Cartoon(title = it.attr("title"), coverUrl = style.group(),
              latest = num, currentTitle = p[0].text(), playDetail = it.attr("href"))
          list.add(a)
        }
      }
      Observable.just(list)
    }
  }
}
