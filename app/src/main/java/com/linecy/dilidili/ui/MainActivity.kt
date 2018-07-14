package com.linecy.dilidili.ui

import android.databinding.ViewDataBinding
import android.os.Bundle
import android.os.SystemClock
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager.OnPageChangeListener
import android.view.MenuItem
import com.linecy.dilidili.R
import com.linecy.dilidili.R.layout
import com.linecy.dilidili.ui.account.ProfileFragment
import com.linecy.dilidili.ui.cartoon.CartoonFragment
import com.linecy.dilidili.ui.home.HomeFragment
import kotlinx.android.synthetic.main.activity_main.bottomNavigation
import kotlinx.android.synthetic.main.activity_main.viewPager

/**
 * app主页
 */
class MainActivity : BaseActivity<ViewDataBinding>() {

  private var menuItem: MenuItem? = null
  private val defaultTime = 2000//双击退出默认时间间隔
  private var lastClickTime = 0L//上次点击时间

  override fun layoutResId(): Int {
    return layout.activity_main
  }


  override fun onInitView(savedInstanceState: Bundle?) {
    hideToolBar()
    val list = ArrayList<Fragment>(3)
    list.add(HomeFragment())
    list.add(CartoonFragment())
    list.add(ProfileFragment())

    viewPager.adapter = FragmentAdapter(supportFragmentManager,
        list)
    viewPager.offscreenPageLimit = 2
    initListener()
  }


  private fun initListener() {
    bottomNavigation.setOnNavigationItemSelectedListener(
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
          when (item.itemId) {
            R.id.navigation_home -> {
              viewPager.currentItem = 0
              return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_cartoon -> {
              viewPager.currentItem = 1
              return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_profile -> {
              viewPager.currentItem = 2
              return@OnNavigationItemSelectedListener true
            }
          }
          false
        })
    viewPager.addOnPageChangeListener(object : OnPageChangeListener {
      override fun onPageScrollStateChanged(state: Int) {
        //ignore
      }

      override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        //ignore
      }

      override fun onPageSelected(position: Int) {
        if (menuItem != null) {
          menuItem?.isChecked = false
        } else {
          bottomNavigation.menu.getItem(0).isChecked = false
        }
        menuItem = bottomNavigation.menu.getItem(position)
        menuItem?.isChecked = true
      }
    })
  }

  /**
   * 双击退出app
   */
  override fun onBackPressed() {
    val current = SystemClock.elapsedRealtime()
    if (current - lastClickTime > defaultTime) {
      lastClickTime = current
      toaster.showText(R.string.exit_app)
    } else {
      finish()
    }
  }

  class FragmentAdapter(manager: FragmentManager,
      private val list: ArrayList<Fragment>) : FragmentPagerAdapter(
      manager) {
    private val titleList = arrayOf("首页", "番剧", "我的")

    override fun getItem(position: Int): Fragment {
      return list[position]
    }

    override fun getPageTitle(position: Int): CharSequence {
      return titleList[position]
    }

    override fun getCount(): Int {
      return list.size
    }
  }
}
