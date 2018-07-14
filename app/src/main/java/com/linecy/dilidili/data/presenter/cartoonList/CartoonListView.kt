package com.linecy.dilidili.data.presenter.cartoonList

import com.linecy.dilidili.data.model.Serials
import com.linecy.module.core.mvp.BaseView

/**
 * @author by linecy.
 */

interface CartoonListView : BaseView {

  fun showCartoonList(list: List<Serials>?)


  fun showError()

}