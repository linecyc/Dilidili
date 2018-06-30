package com.linecy.dilidili.data.presenter.home

import com.linecy.dilidili.data.model.Banner
import com.linecy.dilidili.data.model.Cartoon
import com.linecy.dilidili.data.model.PlayDetail
import com.linecy.module.core.mvp.BaseView

/**
 * @author by linecy.
 */
interface HomeView : BaseView {


  fun showBannerList(banners: List<Banner>?)

  fun showError()

  fun showCartoonDetail(playDetail: PlayDetail)

  fun showCartoonList(cartoons: List<Cartoon>?)

}