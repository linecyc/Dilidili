package com.linecy.dilidili.data.presenter.home

import com.linecy.dilidili.data.model.Banner
import com.linecy.module.core.mvp.BaseView

/**
 * @author by linecy.
 */
interface HomeView : BaseView {


  fun showBanner(list: ArrayList<Banner>?)

  fun showBannerError()

}