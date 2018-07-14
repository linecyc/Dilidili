package com.linecy.dilidili.ui.cartoon

import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import com.linecy.dilidili.R
import com.linecy.dilidili.data.model.Category
import com.linecy.dilidili.data.presenter.cartoonCategory.CategoryPresenter
import com.linecy.dilidili.data.presenter.cartoonCategory.CategoryView
import com.linecy.dilidili.ui.BaseFragment
import com.linecy.dilidili.ui.cartoon.adapter.CartoonCategoryAdapter
import com.linecy.dilidili.ui.widget.sticky.GridStickyDecoration
import com.linecy.dilidili.ui.widget.sticky.GroupInfo
import kotlinx.android.synthetic.main.fragment_cartoon_category.recyclerView
import javax.inject.Inject


/**
 * @author by linecy.
 */
class CartoonCategoryFragment : BaseFragment<ViewDataBinding>(), CategoryView {

  @Inject
  lateinit var categoryPresenter: CategoryPresenter
  private lateinit var adapter: CartoonCategoryAdapter
  private lateinit var gridStickyDecoration: GridStickyDecoration
  override fun layoutResId(): Int {
    return R.layout.fragment_cartoon_category
  }

  override fun onInitView(savedInstanceState: Bundle?) {
    delegatePresenter(categoryPresenter, this)

    initRecyclerView()
    categoryPresenter.getCategory()
  }


  private fun initRecyclerView() {
    adapter = CartoonCategoryAdapter(context!!)
    recyclerView.adapter = adapter
    val manager = GridLayoutManager(context, 5)
    recyclerView.layoutManager = manager
    gridStickyDecoration = GridStickyDecoration(context)
    gridStickyDecoration.setHeaderBackground(ContextCompat.getColor(context!!, R.color.white))
    gridStickyDecoration.setHeaderTextColor(ContextCompat.getColor(context!!, R.color.textPrimary))
    recyclerView.addItemDecoration(gridStickyDecoration)
  }

  override fun showCategory(categories: List<Category>, years: List<Category>) {
    val arr = listOf(GroupInfo(getString(R.string.cartoon_category_time), years.size),
        GroupInfo(getString(R.string.cartoon_category), categories.size))
    gridStickyDecoration.setGroupInfo(arr)
    adapter.refreshData(years, categories)
  }

  override fun showLoading() {
    showLoadingDialog()
  }

  override fun hideLoading() {
    hideLoadingDialog()
  }
}