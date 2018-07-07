package com.linecy.dilidili.ui.splash

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.linecy.dilidili.R
import com.linecy.dilidili.databinding.ActivitySplashBinding
import com.linecy.dilidili.ui.BaseActivity
import com.linecy.dilidili.ui.MainActivity
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_splash.tvCountDown
import kotlinx.android.synthetic.main.activity_splash.tvSkip
import java.util.concurrent.TimeUnit.SECONDS

/**
 * @author by linecy.
 */
class SplashActivity : BaseActivity<ActivitySplashBinding>() {

  //倒计时总时间
  private val time: Long = 5
  private var countDown: Long = time
  private var disposable: Disposable? = null
  override fun layoutResId(): Int {
    return R.layout.activity_splash
  }

  override fun onInitView(savedInstanceState: Bundle?) {
    window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN)
    hideToolBar()
    tvSkip.setOnClickListener {
      goHome()
    }
  }

  override fun onResume() {
    super.onResume()
    initDisposable()
  }


  override fun onPause() {
    super.onPause()
    disposable?.dispose()

  }

  override fun onDestroy() {
    super.onDestroy()
    disposable?.dispose()
  }

  /**
   * 初始化倒计时，
   * 如果中途离开记录时间节点，下次进入在继续，如果销毁，就重新开始。
   */
  private fun initDisposable() {
    disposable?.dispose()
    val start = if (countDown != time) time - countDown else 0L
    disposable = Flowable.intervalRange(start, countDown + 1, 1, 1, SECONDS)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe {
          if (tvCountDown.visibility != View.VISIBLE) {
            tvCountDown.visibility = View.VISIBLE
          }
          countDown = time - it
          tvCountDown.text = countDown.toString().plus("s")
          if (0L == countDown) {
            goHome()

          }
        }
  }

  private fun goHome() {
    startActivity(Intent(this, MainActivity::class.java))
    finish()
  }

  override fun onBackPressed() {
    //ignore
  }
}