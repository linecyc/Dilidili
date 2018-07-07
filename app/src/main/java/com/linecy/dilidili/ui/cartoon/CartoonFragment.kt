package com.linecy.dilidili.ui.cartoon

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.linecy.dilidili.R
import com.linecy.dilidili.databinding.FragmentCartoonBinding
import com.linecy.dilidili.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_cartoon.tabLayout
import kotlinx.android.synthetic.main.fragment_cartoon.viewPager

/**
 * 番剧主入口
 * @author by linecy.
 */
class CartoonFragment : BaseFragment<FragmentCartoonBinding>() {
  override fun layoutResId(): Int {
    return R.layout.fragment_cartoon
  }

  override fun onInitView(savedInstanceState: Bundle?) {
    viewPager.adapter = CartoonPageAdapter(childFragmentManager,
        arrayListOf(CartoonWeekFragment(), CartoonCategoryFragment()))
    tabLayout.setupWithViewPager(viewPager)
  }

  inner class CartoonPageAdapter(fm: FragmentManager,
      private val list: ArrayList<Fragment>) : FragmentPagerAdapter(
      fm) {
    private val titles = arrayListOf(getString(R.string.cartoon_time_list),
        getString(R.string.cartoon_category_list))

    override fun getItem(position: Int): Fragment = list[position]

    override fun getCount(): Int = list.size

    override fun getPageTitle(position: Int): CharSequence? = titles[position]
  }
}