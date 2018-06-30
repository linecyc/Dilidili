package com.linecy.dilidili.ui.home

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager.OnPageChangeListener
import android.view.MenuItem
import com.linecy.dilidili.R
import com.linecy.dilidili.R.layout
import com.linecy.dilidili.databinding.ActivityMainBinding
import com.linecy.dilidili.ui.BaseActivity
import kotlinx.android.synthetic.main.activity_main.bottomNavigation
import kotlinx.android.synthetic.main.activity_main.viewPager

class MainActivity : BaseActivity<ActivityMainBinding>() {

  private var menuItem: MenuItem? = null


  override fun layoutResId(): Int {
    return layout.activity_main
  }


  override fun onInitView(savedInstanceState: Bundle?) {
    hideToolBar()
    val list = ArrayList<Fragment>(3)
    list.add(HomeFragment())
    list.add(CartoonFragment())
    list.add(ProfileFragment())

    viewPager.adapter = FragmentAdapter(supportFragmentManager, list)
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
