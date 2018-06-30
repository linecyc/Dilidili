package com.linecy.dilidili.ui.play

import android.content.pm.ActivityInfo
import android.graphics.Point
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
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
import com.linecy.dilidili.databinding.ActivityPlayBinding
import com.linecy.dilidili.ui.BaseActivity
import com.linecy.dilidili.ui.misc.ijk.IMediaController
import com.linecy.dilidili.ui.play.adapter.CartoonSetAdapter
import com.linecy.dilidili.ui.play.adapter.CartoonSetAdapter.OnCartoonSetClickListener
import com.linecy.dilidili.ui.widget.SimpleIJKVideoPlayer
import kotlinx.android.synthetic.main.activity_play.ivPlayLoading
import kotlinx.android.synthetic.main.activity_play.layout_play_control
import kotlinx.android.synthetic.main.activity_play.recyclerView
import kotlinx.android.synthetic.main.activity_play.videoPlayer
import kotlinx.android.synthetic.main.layout_play_control.groupFull
import kotlinx.android.synthetic.main.layout_play_control.groupMin
import kotlinx.android.synthetic.main.layout_play_control.ivFull
import kotlinx.android.synthetic.main.layout_play_control.ivLock
import kotlinx.android.synthetic.main.layout_play_control.ivMin
import kotlinx.android.synthetic.main.layout_play_control.ivMore
import kotlinx.android.synthetic.main.layout_play_control.ivPlayPause
import kotlinx.android.synthetic.main.layout_play_control.seekBar
import timber.log.Timber
import tv.danmaku.ijk.media.player.IjkMediaPlayer
import javax.inject.Inject

/**
 * @author by linecy.
 */
class PlayActivity : BaseActivity<ActivityPlayBinding>(), PlayView,
    OnCartoonSetClickListener, IMediaController, SeekBar.OnSeekBarChangeListener {

  @Inject
  lateinit var playPresenter: PlayPresenter

  companion object {
    const val EXTRA_CARTOON = "cartoon"
  }

  private var mBackPressed = false

  private lateinit var adapter: CartoonSetAdapter

  override fun layoutResId(): Int {
    return R.layout.activity_play
  }

  override fun onInitView(savedInstanceState: Bundle?) {

    delegatePresenter(playPresenter, this)

    //屏幕常亮
    window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

    val cartoon = intent.getParcelableExtra<Cartoon>(EXTRA_CARTOON)
    setDisplayHomeAsUp(true)
    if (TextUtils.isEmpty(cartoon.title)) {
      setToolbarTitle(cartoon.currentTitle!!)
    } else {
      setToolbarTitle(cartoon.title!!)
    }
    recyclerView.layoutManager = GridLayoutManager(this, 2)
    adapter = CartoonSetAdapter(this)
    adapter.setOnCartoonSetClickListener(this)
    recyclerView.adapter = adapter
    videoPlayer.setUpWithControlView(this)
    IjkMediaPlayer.loadLibrariesOnce(null)
    IjkMediaPlayer.native_profileBegin("libijkplayer.so")

    setClickListener(ivMin, ivFull, ivPlayPause, ivMore, ivLock)
    seekBar.setOnSeekBarChangeListener(this)

    playPresenter.getCartoon(cartoon.playDetail)
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
        if (!ivLock.isSelected) {
          setFullScreen()
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
    }
  }

  /**
   * 加载视频播放地址，如果不是视频地址，抛异常
   */
  override fun showCartoonDetail(playDetail: PlayDetail?) {
    adapter.refreshData(playDetail?.cartoonList)
    if (playDetail != null && !TextUtils.isEmpty(playDetail.playUrl)) {
      if (playDetail.playUrl?.endsWith(".html")!!
          || playDetail.playUrl.endsWith("soresults.dtitle")
          || playDetail.playUrl.contains("v.youku.com")
          || playDetail.playUrl.contains("v.pptv.com")) {
        toaster.showText("网页视频暂不支持解析，请尝试切换源")
        Timber.i("-bad url------->>$playDetail.playUrl")
      } else {
        loadLoading(true)
        videoPlayer.setupWithUrl(playDetail.playUrl)

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

  override fun onCartoonSetClick(cartoon: Cartoon) {
    videoPlayer.onStop()
    playPresenter.getCartoon(cartoon.playDetail)
  }

  override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {}

  override fun onStartTrackingTouch(seekBar: SeekBar?) {}

  override fun onStopTrackingTouch(seekBar: SeekBar?) {
    videoPlayer.onSeekTo((seekBar?.progress!! * 1000).toLong())
  }

  /**
   * 更新进度条
   */
  override fun onSeekUpdate(seek: Long) {
    seekBar.progress = (seek / 1000).toInt()
    Timber.i("seekbar------------->>${seekBar.progress}")

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
        seekBar.max = (player?.duration!! / 1000).toInt()
        Timber.i("seekbar------------->>${seekBar.max}")
      }
      SimpleIJKVideoPlayer.STATE_START, SimpleIJKVideoPlayer.STATE_PLAY -> {
        loadLoading(false)
        ivPlayPause.isSelected = true
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
      mBackPressed = true
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
    if (requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
      requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
      window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
      val display = windowManager.defaultDisplay
      val size = Point()
      display.getSize(size)
      videoPlayer.layoutParams = ConstraintLayout.LayoutParams(size.x,
          (size.x * 9 / 16f).toInt())
      showToolBar()
      recyclerView.visibility = View.VISIBLE
      groupFull.visibility = View.GONE
      groupMin.visibility = View.VISIBLE
    } else {
      requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
      window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
      videoPlayer.layoutParams = ConstraintLayout.LayoutParams(
          ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT)
      hideToolBar()
      recyclerView.visibility = View.GONE
      groupFull.visibility = View.VISIBLE
      groupMin.visibility = View.GONE
    }
  }
}