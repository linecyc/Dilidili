package com.linecy.dilidili.ui.cartoon

import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.View
import android.view.View.OnClickListener
import com.linecy.dilidili.R
import com.linecy.dilidili.data.model.Cartoon
import com.linecy.dilidili.data.presenter.cartoonWeek.CartoonWeekPresenter
import com.linecy.dilidili.data.presenter.cartoonWeek.CartoonWeekView
import com.linecy.dilidili.ui.BaseFragment
import com.linecy.dilidili.ui.cartoon.adapter.CartoonWeekAdapter
import com.linecy.dilidili.ui.misc.ViewContainer.OnReloadCallBack
import kotlinx.android.synthetic.main.fragment_cartoon_week.recyclerView
import kotlinx.android.synthetic.main.fragment_cartoon_week.viewContainer
import kotlinx.android.synthetic.main.fragment_cartoon_week.weekday1
import kotlinx.android.synthetic.main.fragment_cartoon_week.weekday2
import kotlinx.android.synthetic.main.fragment_cartoon_week.weekday3
import kotlinx.android.synthetic.main.fragment_cartoon_week.weekday4
import kotlinx.android.synthetic.main.fragment_cartoon_week.weekday5
import kotlinx.android.synthetic.main.fragment_cartoon_week.weekday6
import kotlinx.android.synthetic.main.fragment_cartoon_week.weekday7
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

/**
 * @author by linecy.
 */
class CartoonWeekFragment : BaseFragment<ViewDataBinding>(),
    CartoonWeekView, OnClickListener, OnReloadCallBack {


  private var current: Int//默认加载"今天"

  init {
    val c = Calendar.getInstance(Locale.CHINA)
    current = formatDay(c.get(Calendar.DAY_OF_WEEK))
  }

  @Inject
  lateinit var cartoonWeekPresenter: CartoonWeekPresenter

  private var weekAdapter: CartoonWeekAdapter? = null

  override fun layoutResId(): Int {
    return R.layout.fragment_cartoon_week
  }

  override fun onInitView(savedInstanceState: Bundle?) {
    delegatePresenter(cartoonWeekPresenter, this)
    viewContainer.setOnReloadCallBack(this)
    val manager = GridLayoutManager(context, 3)
    recyclerView.layoutManager = manager
    weekAdapter = CartoonWeekAdapter(context!!)
    recyclerView.adapter = weekAdapter
    setListener(weekday1, weekday2, weekday3, weekday4, weekday5, weekday6, weekday7)

    cartoonWeekPresenter.getWeekCartoon()

  }

  private fun setListener(vararg views: View) {
    for (i in 0 until views.size) {
      views[i].setOnClickListener(this)
      views[i].isSelected = i == current
    }
  }

  private fun setSelector(view: View) {
    if (!view.isSelected) {
      listOf(weekday7, weekday1, weekday2, weekday3, weekday4, weekday5, weekday6).forEach {
        it.isSelected = it == view
      }
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    cartoonWeekPresenter.detach()
  }

  override fun showLoading() {
    showLoadingDialog()
  }

  override fun hideLoading() {
    hideLoadingDialog()
  }

  override fun onReload() {
    cartoonWeekPresenter.getWeekCartoon()
  }

  override fun onClick(v: View?) {
    when (v?.id) {
      R.id.weekday7
      -> {
        weekAdapter?.refreshData(1)
        setSelector(weekday7)
      }
      R.id.weekday1
      -> {
        weekAdapter?.refreshData(2)
        setSelector(weekday1)
      }
      R.id.weekday2
      -> {
        weekAdapter?.refreshData(3)
        setSelector(weekday2)
      }
      R.id.weekday3
      -> {
        weekAdapter?.refreshData(4)
        setSelector(weekday3)
      }
      R.id.weekday4
      -> {
        weekAdapter?.refreshData(5)
        setSelector(weekday4)
      }
      R.id.weekday5
      -> {
        weekAdapter?.refreshData(6)
        setSelector(weekday5)
      }
      R.id.weekday6
      -> {
        weekAdapter?.refreshData(7)
        setSelector(weekday6)
      }
    }
  }

  override fun showWeekList(list: ArrayList<ArrayList<Cartoon>>) {
    viewContainer.setDisplayedChildId(R.id.content)
    weekAdapter?.refreshData(list, current)
  }

  override fun showError() {
    viewContainer.setDisplayedChildId(R.id.error)
  }

  /**
   * 转换时间
   */
  private fun formatDay(position: Int): Int {
    if (position > 7 || position < 0) {
      throw IllegalArgumentException("The week day must be start 1 to 7,and ")
    }
    return when (position) {
      1 -> 6
      else -> position - 2
    }
  }
}