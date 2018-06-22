package com.linecy.dilidili.ui.home

import android.os.Bundle
import com.linecy.dilidili.R
import com.linecy.dilidili.data.model.Banner
import com.linecy.dilidili.data.presenter.home.HomePresenter
import com.linecy.dilidili.data.presenter.home.HomeView
import com.linecy.dilidili.databinding.FragmentHomeBinding
import com.linecy.dilidili.ui.BaseFragment
import javax.inject.Inject

class HomeFragment : BaseFragment<FragmentHomeBinding>(), HomeView {


  @Inject
  lateinit var presenter: HomePresenter

  override fun layoutResId(): Int {
    return R.layout.fragment_home
  }

  override fun onInitView(savedInstanceState: Bundle?) {

    //delegatePresenter(presenter, this)
    presenter.attach(this)
    presenter.getBanners()
  }

  override fun showBanner(list: ArrayList<Banner>?) {

  }

  override fun showBannerError() {
  }

  override fun showLoading() {
  }

  override fun hideLoading() {
  }
}
