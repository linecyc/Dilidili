package com.linecy.dilidili.data.presenter.play

import com.linecy.dilidili.data.model.PlayDetail
import com.linecy.module.core.mvp.BaseView

/**
 * @author by linecy.
 */
interface PlayView : BaseView {

  fun showCartoonDetail(playDetail: PlayDetail?)

  fun showError(error: String?)
}