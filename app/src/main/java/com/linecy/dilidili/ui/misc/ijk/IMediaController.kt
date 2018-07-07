package com.linecy.dilidili.ui.misc.ijk

import android.widget.SeekBar
import com.linecy.dilidili.ui.widget.SimpleIJKVideoPlayer

interface IMediaController {

  fun onHide()

  fun onShow()
  //fixme changed update 两接口是否能合并？
  //fun onSeekUpdate(seek: Long, isShowing: Boolean)

  fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean, isShowing: Boolean)

  fun onStartTrackingTouch(seekBar: SeekBar?)

  fun onStopTrackingTouch(seekBar: SeekBar?)

  fun onPlayStateChanged(state: Int, player: SimpleIJKVideoPlayer?)
}
