package com.linecy.dilidili.ui.cartoon

import android.content.Intent
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.linecy.dilidili.BR
import com.linecy.dilidili.R
import com.linecy.dilidili.data.model.Category
import com.linecy.dilidili.data.model.Serials
import com.linecy.dilidili.data.presenter.cartoonList.CartoonListPresenter
import com.linecy.dilidili.data.presenter.cartoonList.CartoonListView
import com.linecy.dilidili.ui.BaseActivity
import com.linecy.dilidili.ui.adapter.CustomAdapter
import com.linecy.dilidili.ui.adapter.CustomAdapter.OnItemClickListener
import com.linecy.dilidili.ui.misc.ViewContainer.OnReloadCallBack
import com.linecy.dilidili.ui.play.SerialsActivity
import kotlinx.android.synthetic.main.activity_cartoon_list.recyclerView
import kotlinx.android.synthetic.main.activity_cartoon_list.swipeLayout
import kotlinx.android.synthetic.main.activity_cartoon_list.viewContainer
import javax.inject.Inject

/**
 * 不同类别下动漫列表
 * @author by linecy.
 */
class CartoonListActivity : BaseActivity<ViewDataBinding>(), CartoonListView,
    OnItemClickListener, OnReloadCallBack, OnRefreshListener {


  companion object {
    const val EXTRA_DATA = "extra_data"
  }

  @Inject
  lateinit var cartoonListPresenter: CartoonListPresenter
  lateinit var adapter: CustomAdapter
  private lateinit var link: String

  override fun layoutResId(): Int {
    return R.layout.activity_cartoon_list
  }

  override fun onInitView(savedInstanceState: Bundle?) {
    delegatePresenter(cartoonListPresenter, this)
    val category = intent.getParcelableExtra<Category>(EXTRA_DATA)
    setDisplayHomeAsUp(true)
    setToolbarTitle(category.title)
    viewContainer.setOnReloadCallBack(this)
    swipeLayout.setColorSchemeResources(R.color.colorPrimary)
    swipeLayout.setOnRefreshListener(this)

    adapter = CustomAdapter(this, intArrayOf(R.layout.item_serials), intArrayOf(BR.itemSerials))
    adapter.setOnItemClickListener(this)
    recyclerView.adapter = adapter
    recyclerView.layoutManager = LinearLayoutManager(this)

    link = category.link
    cartoonListPresenter.getCartoonList(link)
  }

  override fun onReload() {
    onRefresh()
  }

  override fun onRefresh() {
    cartoonListPresenter.getCartoonList(link)
  }


  override fun showCartoonList(list: List<Serials>?) {
    viewContainer.setDisplayedChildId(R.id.swipeLayout)
    adapter.refreshData(list, null)
  }

  override fun onItemClick(view: View, data: Any?) {
    if (data is Serials) {
      val intent = Intent(this, SerialsActivity::class.java)
      intent.putExtra(SerialsActivity.EXTRA_DATA, data.link)
      startActivity(intent)
    }
  }

  override fun showError() {
    viewContainer.setDisplayedChildId(R.id.error)
  }

  override fun showLoading() {
    if (!swipeLayout.isRefreshing) {
      showLoadingDialog()
    }
  }

  override fun hideLoading() {
    hideLoadingDialog()
    swipeLayout.isRefreshing = false
  }


}