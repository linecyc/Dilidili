package com.linecy.dilidili.data.presenter.home

import com.linecy.dilidili.data.datasource.HomeRepository
import com.linecy.dilidili.data.model.Banner
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

class HomePresenter @Inject constructor(
    private val repository: HomeRepository) : RxPresenter<HomeView>() {

  fun getBanners() {
    baseView?.showLoading()
    disposables.add(repository.getBanners().map {
      it.select("#slider div.shade a")
    }.flatMap {
      val list = ArrayList<Banner>()
      it.forEach {
        val p = Pattern.compile("(?<=background: url\\()(.*)(?=\\) )")
        val m = p.matcher(it.attr("style"))

        if (m.find()) {
          list.add(Banner(m.group(), it.text(), it.attr("href")))
        }
      }
      Observable.just(list)
    }.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread()).subscribe {
          Timber.i("------------" + it.toString())
        })
  }
}
