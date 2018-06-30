package com.linecy.dilidili.ui.misc.ijk

import com.linecy.dilidili.ui.widget.SimpleIJKVideoPlayer

interface IMediaController {

  fun onHide()

  fun onShow()

  fun onSeekUpdate(seek: Long)

  fun onPlayStateChanged(state: Int, player: SimpleIJKVideoPlayer?)
}
