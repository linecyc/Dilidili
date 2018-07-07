package com.linecy.dilidili.data.presenter.cartoonCategory

import com.linecy.dilidili.data.model.Category
import com.linecy.module.core.mvp.BaseView

/**
 * @author by linecy.
 */
interface CategoryView : BaseView {

  fun showCategory(categories: List<Category>, years: List<Category>)

}