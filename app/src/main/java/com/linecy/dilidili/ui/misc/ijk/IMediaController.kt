package com.linecy.dilidili.ui.misc.ijk

import android.widget.SeekBar
import com.linecy.dilidili.ui.widget.SimpleIJKVideoPlayer

interface IMediaController {

  fun onHide()

  fun onShow()

  fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean, isShowing: Boolean)

  fun onStartTrackingTouch(seekBar: SeekBar?)

  fun onStopTrackingTouch(seekBar: SeekBar?)

  fun onPlayStateChanged(state: Int, player: SimpleIJKVideoPlayer?)
}
