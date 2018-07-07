package com.linecy.dilidili.data.presenter.cartoonWeek

import com.linecy.dilidili.data.model.Cartoon
import com.linecy.module.core.mvp.BaseView

/**
 * @author by linecy.
 */
interface CartoonWeekView : BaseView {


  fun showWeekList(list: ArrayList<ArrayList<Cartoon>>)


  fun showError()
}