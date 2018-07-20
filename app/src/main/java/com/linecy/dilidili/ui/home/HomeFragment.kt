package com.linecy.dilidili.ui.home

import android.content.Intent
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.linecy.banner.listener.OnBannerClickListener
import com.linecy.dilidili.R
import com.linecy.dilidili.data.model.Banner
import com.linecy.dilidili.data.model.Cartoon
import com.linecy.dilidili.data.model.PlayDetail
import com.linecy.dilidili.data.presenter.home.HomePresenter
import com.linecy.dilidili.data.presenter.home.HomeView
import com.linecy.dilidili.ui.BaseFragment
import com.linecy.dilidili.ui.home.adapter.CartoonAdapter
import com.linecy.dilidili.ui.misc.ViewContainer
import com.linecy.dilidili.ui.play.PlayActivity
import com.linecy.dilidili.ui.search.SearchActivity
import kotlinx.android.synthetic.main.fragment_home.appBarLayout
import kotlinx.android.synthetic.main.fragment_home.bannerView
import kotlinx.android.synthetic.main.fragment_home.fab
import kotlinx.android.synthetic.main.fragment_home.layoutSearch
import kotlinx.android.synthetic.main.fragment_home.recyclerView
import kotlinx.android.synthetic.main.fragment_home.swipeLayout
import kotlinx.android.synthetic.main.fragment_home.viewContainer
import kotlinx.android.synthetic.main.layout_search.etSearch
import javax.inject.Inject

class HomeFragment : BaseFragment<ViewDataBinding>(), HomeView,
    SwipeRefreshLayout.OnRefreshListener, AppBarLayout.OnOffsetChangedListener,
    ViewContainer.OnReloadCallBack, OnBannerClickListener<Banner> {

  @Inject
  lateinit var presenter: HomePresenter

  private lateinit var adapter: CartoonAdapter
  private var isFirstVisible = true
  private var isNotOffset = true

  override fun layoutResId(): Int {
    return R.layout.fragment_home
  }

  @Suppress("UNCHECKED_CAST")
  override fun onInitView(savedInstanceState: Bundle?) {

    delegatePresenter(presenter, this)

    swipeLayout.setColorSchemeResources(R.color.colorPrimary)
    swipeLayout.setOnRefreshListener(this)
    appBarLayout.addOnOffsetChangedListener(this)
    bannerView.setupWithBannerCreator(AdCreator())
        .setOnBannerClickListener(this)

    val manager = GridLayoutManager(context, 2)
    recyclerView.layoutManager = manager
    adapter = CartoonAdapter(context!!)
    recyclerView.adapter = adapter
    recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
      override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        isFirstVisible = manager
            .findFirstCompletelyVisibleItemPosition() == 0
        swipeLayout.isEnabled = isFirstVisible && isNotOffset

      }
    })
    fab.setOnClickListener {
      recyclerView.scrollToPosition(0)
    }
    fab.visibility = View.INVISIBLE
    fab.isEnabled = false
    etSearch.isFocusable = false
    layoutSearch.setOnClickListener {
      startActivity(Intent(context, SearchActivity::class.java))
    }
    etSearch.setOnClickListener {
      startActivity(Intent(context, SearchActivity::class.java))
    }
    viewContainer.setOnReloadCallBack(this)
    presenter.getHomeData()
  }

  override fun onBannerClick(data: Banner, position: Int) {
    val intent = Intent(activity, PlayActivity::class.java)
    intent.putExtra(PlayActivity.EXTRA_CARTOON,
        Cartoon(title = data.title, coverUrl = data.srcUrl, playDetail = data.linkUrl))
    startActivity(intent)
  }

  override fun onDestroyView() {
    super.onDestroyView()
    appBarLayout.removeOnOffsetChangedListener(this)
  }

  override fun onRefresh() {
    presenter.getHomeData()
  }

  override fun onReload() {
    onRefresh()
  }

  override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
    isNotOffset = verticalOffset == 0
    swipeLayout.isEnabled = isFirstVisible && isNotOffset
  }

  override fun showBannerList(banners: List<Banner>?) {
    viewContainer.setDisplayedChildId(R.id.swipeLayout)
    bannerView.onRefreshData(banners)
  }

  override fun showCartoonList(cartoons: List<Cartoon>?) {
    adapter.refreshData(cartoons)
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

  override fun showCartoonDetail(playDetail: PlayDetail) {
    val intent = Intent(activity, PlayActivity::class.java)
    intent.putExtra(PlayActivity.EXTRA_CARTOON, playDetail)
    startActivity(intent)
  }
}
