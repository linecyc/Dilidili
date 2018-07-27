package com.linecy.dilidili.ui.play

import android.content.Intent
import android.content.pm.ActivityInfo
import android.databinding.ViewDataBinding
import android.graphics.Point
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.os.SystemClock
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.GridLayoutManager
import android.text.TextUtils
import android.view.View
import android.view.WindowManager
import android.widget.SeekBar
import com.linecy.dilidili.R
import com.linecy.dilidili.data.model.Cartoon
import com.linecy.dilidili.data.model.PlayDetail
import com.linecy.dilidili.data.presenter.play.PlayPresenter
import com.linecy.dilidili.data.presenter.play.PlayView
import com.linecy.dilidili.di.module.GlideApp
import com.linecy.dilidili.ui.BaseActivity
import com.linecy.dilidili.ui.account.SettingsActivity
import com.linecy.dilidili.ui.misc.GlideCircleTransform
import com.linecy.dilidili.ui.misc.Settings
import com.linecy.dilidili.ui.misc.ViewContainer
import com.linecy.dilidili.ui.misc.ijk.IMediaController
import com.linecy.dilidili.ui.play.adapter.CartoonSetAdapter
import com.linecy.dilidili.ui.play.adapter.CartoonSetAdapter.OnCartoonSetClickListener
import com.linecy.dilidili.ui.widget.SimpleIJKVideoPlayer
import com.linecy.dilidili.utils.TimeUtils
import kotlinx.android.synthetic.main.activity_play.ivAvatar
import kotlinx.android.synthetic.main.activity_play.ivPlayLoading
import kotlinx.android.synthetic.main.activity_play.layout_play_control
import kotlinx.android.synthetic.main.activity_play.listGroup
import kotlinx.android.synthetic.main.activity_play.recyclerView
import kotlinx.android.synthetic.main.activity_play.tvDetail
import kotlinx.android.synthetic.main.activity_play.videoPlayer
import kotlinx.android.synthetic.main.activity_play.viewContainer
import kotlinx.android.synthetic.main.layout_play_control.groupFull
import kotlinx.android.synthetic.main.layout_play_control.groupMin
import kotlinx.android.synthetic.main.layout_play_control.ivFull
import kotlinx.android.synthetic.main.layout_play_control.ivLock
import kotlinx.android.synthetic.main.layout_play_control.ivMin
import kotlinx.android.synthetic.main.layout_play_control.ivMore
import kotlinx.android.synthetic.main.layout_play_control.ivPlayPause
import kotlinx.android.synthetic.main.layout_play_control.seekBar
import kotlinx.android.synthetic.main.layout_play_control.tvCurrent
import kotlinx.android.synthetic.main.layout_play_control.tvDrag
import kotlinx.android.synthetic.main.layout_play_control.tvDuration
import timber.log.Timber
import tv.danmaku.ijk.media.player.IjkMediaPlayer
import javax.inject.Inject

/**
 * 播放页面
 * @author by linecy.
 */
class PlayActivity : BaseActivity<ViewDataBinding>(), PlayView,
    OnCartoonSetClickListener, IMediaController, ViewContainer.OnReloadCallBack {

  companion object {
    const val EXTRA_CARTOON = "cartoon"
  }

  @Inject
  lateinit var playPresenter: PlayPresenter
  @Inject
  lateinit var settings: Settings

  private val _settingRequestCode = 12
  private val _defaultTime = 400
  private lateinit var adapter: CartoonSetAdapter
  private var currentLink: String? = null//当前页面链接
  private var lastClickTime = 0L

  override fun layoutResId(): Int {
    return R.layout.activity_play
  }

  override fun onInitView(savedInstanceState: Bundle?) {

    delegatePresenter(playPresenter, this)

    hideToolBar()
    viewContainer.setOnReloadCallBack(this)
    //屏幕常亮
    window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

    val cartoon = intent.getParcelableExtra<Cartoon>(EXTRA_CARTOON)
    val manager = GridLayoutManager(this, 2)
    recyclerView.layoutManager = manager
    adapter = CartoonSetAdapter(this)
    adapter.setOnCartoonSetClickListener(this)
    recyclerView.adapter = adapter
    videoPlayer.setHardDecode(settings.isHardCodec())
    videoPlayer.setUpWithControlView(this)
    videoPlayer.setOnClickListener(this)
    layout_play_control.setOnClickListener(this)
    videoPlayer.setUpWithSeekBar(seekBar)
    IjkMediaPlayer.loadLibrariesOnce(null)
    IjkMediaPlayer.native_profileBegin("libijkplayer.so")

    setClickListener(ivMin, ivFull, ivPlayPause, ivMore, ivLock)
    if (null != cartoon && !TextUtils.isEmpty(cartoon.playDetail)) {
      currentLink = cartoon.playDetail
      playPresenter.getCartoon(currentLink!!)
    } else {
      toaster.showText("未能解析当前播放链接")
    }
  }

  /**
   * 批量设置监听
   */
  private fun setClickListener(vararg views: View) {
    views.forEach {
      it.setOnClickListener(this)
    }
  }

  override fun onClick(p0: View?) {
    super.onClick(p0)
    when (p0?.id) {
      R.id.ivFull -> {
        if (!ivLock.isSelected) {
          setFullScreen()
        }
      }
      R.id.ivMin -> {
        if (ivMin.isSelected) {
          if (!ivLock.isSelected) {
            setFullScreen()
          }
        } else {
          finish()
        }
      }
      R.id.ivPlayPause -> {
        if (!ivLock.isSelected) {
          if (ivPlayPause.isSelected) {
            ivPlayPause.isSelected = false
            videoPlayer.onPause()
          } else {
            ivPlayPause.isSelected = true
            videoPlayer.onStart()
          }
        }
      }
      R.id.ivLock -> {
        ivLock.isSelected = !ivLock.isSelected
      }
      R.id.ivMore -> {
        startActivityForResult(Intent(this, SettingsActivity::class.java), _settingRequestCode)
      }
      R.id.videoPlayer, R.id.layout_play_control -> {
        val current = SystemClock.elapsedRealtime()
        if (current - lastClickTime > _defaultTime) {
          lastClickTime = current
        } else if (requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
          setFullScreen()
        }
      }
    }
  }

  override fun onReload() {
    playPresenter.getCartoon(currentLink!!)
  }

  /**
   * 加载视频播放地址，如果不是视频地址，抛异常
   */
  override fun showCartoonDetail(playDetail: PlayDetail?) {
    viewContainer.setDisplayedChildId(R.id.content)
    adapter.refreshData(playDetail?.cartoonList, currentLink)
    if (playDetail != null && !TextUtils.isEmpty(playDetail.playUrl)) {
      if (playDetail.playUrl?.endsWith(".html")!!
          || playDetail.playUrl.endsWith("soresults.dtitle")
          || playDetail.playUrl.contains("v.youku.com")
          || playDetail.playUrl.contains("iqiyi.com")
          || playDetail.playUrl.contains("v.pptv.com")) {
        toaster.showText("网页视频暂不支持解析")
        Timber.i("-bad url------->>$playDetail.playUrl")
      } else {
        loadLoading(true)
        videoPlayer.setupWithUrl(playDetail.playUrl)
      }
      tvDetail.text = playDetail.title
      GlideApp.with(this)
          .load(playDetail.coverUrl)
          .transform(GlideCircleTransform())
          .into(ivAvatar)
    } else {
      toaster.showText("未能解析到播放地址")
    }
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    if (requestCode == _settingRequestCode) {
      videoPlayer.setHardDecode(settings.isHardCodec())
      if (videoPlayer.getPlayState() == SimpleIJKVideoPlayer.STATE_COMPLETION) {
        if (settings.isAutoPlayNext()) {
          val link = adapter.getNextLink()
          if (TextUtils.isEmpty(link)) {
            toaster.showText(R.string.play_last)
          } else {
            this.currentLink = link
            toaster.showText(R.string.play_next)
            videoPlayer.onStop()
            playPresenter.getCartoon(link!!)
          }
        }
      }
    }
  }

  override fun showLoading() {
    showLoadingDialog()
  }

  override fun hideLoading() {
    hideLoadingDialog()
  }

  override fun showError(error: String?) {

  }

  override fun onCartoonSetClick(cartoon: Cartoon, position: Int) {
    this.currentLink = cartoon.playDetail
    videoPlayer.onStop()
    playPresenter.getCartoon(cartoon.playDetail)
  }

  override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean,
      isShowing: Boolean) {
    if (fromUser) {
      val s = TimeUtils.formatTime(progress)
      if (!TextUtils.isEmpty(s))
        if (tvDrag.visibility != View.VISIBLE) {
          tvDrag.visibility = View.VISIBLE
        }
      tvDrag.text = s
    } else {
      if (isShowing) {
        tvCurrent.text = TimeUtils.formatTime(seekBar?.progress!!)
      }
    }
  }

  override fun onStartTrackingTouch(seekBar: SeekBar?) {}

  override fun onStopTrackingTouch(seekBar: SeekBar?) {
    if (tvDrag.visibility == View.VISIBLE) {
      tvDrag.visibility = View.GONE
    }
  }

  /**
   * 播放状态改变时处理UI
   */
  override fun onPlayStateChanged(state: Int, player: SimpleIJKVideoPlayer?) {
    when (state) {
      SimpleIJKVideoPlayer.STATE_BUFFER -> {
        loadLoading(true)
        ivPlayPause.isSelected = false
      }
      SimpleIJKVideoPlayer.STATE_PREPARED -> {
        loadLoading(false)
        ivPlayPause.isSelected = true
      }
      SimpleIJKVideoPlayer.STATE_START, SimpleIJKVideoPlayer.STATE_PLAY -> {
        loadLoading(false)
        ivPlayPause.isSelected = true

        seekBar.max = (player?.duration!! / 1000).toInt()
        tvDuration.text = TimeUtils.formatTime(seekBar.max)
      }
      SimpleIJKVideoPlayer.STATE_COMPLETION -> {
        //修复刚好在控制层隐藏时播放结束进度没有更新的问题
        tvCurrent.text = tvDuration.text
        loadLoading(false)
        ivPlayPause.isSelected = false
        //自动换p
        if (settings.isAutoPlayNext()) {
          val link = adapter.getNextLink()
          if (TextUtils.isEmpty(link)) {
            toaster.showText(R.string.play_last)
          } else {
            this.currentLink = link
            toaster.showText(R.string.play_next)
            videoPlayer.onStop()
            playPresenter.getCartoon(link!!)
          }
        }
      }
      else -> {
        loadLoading(false)
        ivPlayPause.isSelected = false
      }
    }
  }

  override fun onHide() {
    layout_play_control.visibility = View.GONE
  }

  override fun onShow() {
    layout_play_control.visibility = View.VISIBLE
  }

  override fun onBackPressed() {
    //全屏时返回，切换为竖屏,同时解锁
    if (requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
      ivLock.isSelected = false
      setFullScreen()
    } else {
      super.onBackPressed()
    }
  }

  override fun onStop() {
    super.onStop()
    videoPlayer.onStop()
    IjkMediaPlayer.native_profileEnd()
    loadLoading(false)

  }

  private fun loadLoading(isLoading: Boolean) {
    val animation: AnimationDrawable = ivPlayLoading.drawable as AnimationDrawable
    if (isLoading) {
      if (ivPlayLoading.visibility != View.VISIBLE) {
        ivPlayLoading.visibility = View.VISIBLE
        animation.start()
      }
    } else {
      if (ivPlayLoading.visibility != View.GONE) {
        animation.stop()
        ivPlayLoading.visibility = View.GONE
      }
    }
  }


  /**
   * 横竖屏切换
   */
  private fun setFullScreen() {
    ivMin.isSelected = !ivMin.isSelected
    if (requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
      requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
      window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
      val display = windowManager.defaultDisplay
      val size = Point()
      display.getSize(size)
      videoPlayer.layoutParams = ConstraintLayout.LayoutParams(size.x,
          (size.x * 9 / 16f).toInt())
      listGroup.visibility = View.VISIBLE
      groupFull.visibility = View.GONE
      groupMin.visibility = View.VISIBLE
    } else {
      requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
      window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
      videoPlayer.layoutParams = ConstraintLayout.LayoutParams(
          ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT)
      listGroup.visibility = View.GONE
      groupFull.visibility = View.VISIBLE
      groupMin.visibility = View.GONE
    }
  }
}