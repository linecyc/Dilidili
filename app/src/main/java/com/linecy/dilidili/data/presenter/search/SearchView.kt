package com.linecy.dilidili.data.presenter.search

import com.linecy.dilidili.data.model.SearchResult
import com.linecy.module.core.mvp.BaseView

/**
 * @author by linecy.
 */
interface SearchView : BaseView {


  fun showResult(result: SearchResult)

  fun showError()

  fun showMore(result: SearchResult)

}