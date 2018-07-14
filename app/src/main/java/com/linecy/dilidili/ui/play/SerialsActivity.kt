package com.linecy.dilidili.ui.play

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.AppBarLayout.OnOffsetChangedListener
import android.support.v4.view.PagerAdapter
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.linecy.dilidili.BR
import com.linecy.dilidili.R
import com.linecy.dilidili.data.model.Cartoon
import com.linecy.dilidili.data.model.Serials
import com.linecy.dilidili.data.model.SerialsDetail
import com.linecy.dilidili.data.presenter.cartoonDetail.SerialsPresenter
import com.linecy.dilidili.data.presenter.cartoonDetail.SerialsView
import com.linecy.dilidili.databinding.ActivitySerialsBinding
import com.linecy.dilidili.ui.BaseActivity
import com.linecy.dilidili.ui.adapter.CustomAdapter
import com.linecy.dilidili.ui.misc.ViewContainer.OnReloadCallBack
import kotlinx.android.synthetic.main.activity_serials.appBarLayout
import kotlinx.android.synthetic.main.activity_serials.swipeLayout
import kotlinx.android.synthetic.main.activity_serials.tabLayout
import kotlinx.android.synthetic.main.activity_serials.viewContainer
import kotlinx.android.synthetic.main.activity_serials.viewPager
import javax.inject.Inject

/**
 * 某动画当季详情页
 * @author by linecy.
 */
class SerialsActivity : BaseActivity<ActivitySerialsBinding>(), SerialsView,
    OnRefreshListener, OnReloadCallBack, OnOffsetChangedListener {


  companion object {
    const val EXTRA_DATA = "extra_data"
  }

  @Inject
  lateinit var serialsPresenter: SerialsPresenter

  private lateinit var page1: TextView//简介
  private lateinit var page2: RecyclerView//剧集
  private lateinit var page3: RecyclerView//推荐

  private lateinit var page2Adapter: CustomAdapter
  private lateinit var page3Adapter: CustomAdapter
  private lateinit var link: String

  override fun layoutResId(): Int {
    return R.layout.activity_serials
  }

  override fun onInitView(savedInstanceState: Bundle?) {
    delegatePresenter(serialsPresenter, this)
    setDisplayHomeAsUp(true)
    swipeLayout.setColorSchemeResources(R.color.colorPrimary)
    swipeLayout.setOnRefreshListener(this)
    viewContainer.setOnReloadCallBack(this)
    appBarLayout.addOnOffsetChangedListener(this)
    init()

    link = intent.getStringExtra(EXTRA_DATA)
    serialsPresenter.getSerials(link)
  }

  /**
   * 初始化tabLayout、viewpager、recyclerView
   */
  private fun init() {
    val lp = swipeLayout.layoutParams
    page1 = TextView(this)
    page1.layoutParams = lp
    page2 = RecyclerView(this)
    page2.layoutParams = lp
    page3 = RecyclerView(this)
    page3.layoutParams = lp

    page1.setPadding(0, 16, 0, 0)
    val manager = GridLayoutManager(this, 2)
    page2.layoutManager = manager
    page2Adapter = CustomAdapter(this, intArrayOf(R.layout.item_serials_labels),
        intArrayOf(BR.itemSerialSeason))
    page2Adapter.setOnItemClickListener(onItemClickListener = object : CustomAdapter.OnItemClickListener {
      override fun onItemClick(view: View, data: Any?) {
        val intent = Intent(this@SerialsActivity, PlayActivity::class.java)
        intent.putExtra(PlayActivity.EXTRA_CARTOON, (data as Serials).cartoons?.get(0))
        startActivity(intent)
      }
    })
    page2.adapter = page2Adapter

    page3.layoutManager = GridLayoutManager(this, 3)

    page3Adapter = CustomAdapter(this,
        intArrayOf(R.layout.item_recommend_cartoon),
        intArrayOf(BR.itemRecommendCartoon))
    page3Adapter.setOnItemClickListener(onItemClickListener = object : CustomAdapter.OnItemClickListener {
      override fun onItemClick(view: View, data: Any?) {
        //fixme 点击推荐是重新刷新还是再次跳转呢
        val intent = Intent(this@SerialsActivity, SerialsActivity::class.java)
        intent.putExtra(SerialsActivity.EXTRA_DATA, (data as Cartoon).playDetail)
        startActivity(intent)
      }
    })
    page3.adapter = page3Adapter

    val list = listOf(page1, page2, page3)
    viewPager.adapter = ViewPageAdapter(list)
    viewPager.offscreenPageLimit = 2
    tabLayout.setupWithViewPager(viewPager)
  }

  override fun onDestroy() {
    super.onDestroy()
    appBarLayout.removeOnOffsetChangedListener(this)
  }

  override fun onReload() {
    onRefresh()
  }

  override fun onRefresh() {
    serialsPresenter.getSerials(link)
  }

  override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
    swipeLayout.isEnabled = verticalOffset == 0
  }

  /**
   * 填充数据
   */
  override fun showSerialsDetail(serialsDetail: SerialsDetail?) {
    viewContainer.setDisplayedChildId(R.id.swipeLayout)
    if (serialsDetail != null) {
      setToolbarTitle(serialsDetail.listSerial[0].title!!)
      page1.text = serialsDetail.listSerial[0].detail
      page2Adapter.refreshData(serialsDetail.listSerial)
      page3Adapter.refreshData(serialsDetail.recommend2)

      mDataBinding?.setVariable(BR.serials, serialsDetail.listSerial[0])
      mDataBinding?.executePendingBindings()
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

  inner class ViewPageAdapter(private val list: List<View>) : PagerAdapter() {
    private val titles = listOf(getString(R.string.introduction), getString(R.string.serials),
        getString(R.string.recommend))

    override fun isViewFromObject(view: View, any: Any): Boolean {
      return view == any
    }

    override fun getCount(): Int {
      return list.size
    }

    override fun destroyItem(container: ViewGroup, position: Int, any: Any) {
      container.removeView(any as View)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
      container.addView(list[position])
      return list[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
      return titles[position]
    }

  }
}