package com.linecy.dilidili.data.presenter.cartoonDetail

import com.linecy.dilidili.data.model.SerialsDetail
import com.linecy.module.core.mvp.BaseView

/**
 * @author by linecy.
 */

interface SerialsView : BaseView {

  fun showSerialsDetail(serialsDetail: SerialsDetail?)


  fun showError()

}