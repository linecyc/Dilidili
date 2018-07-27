package com.linecy.dilidili.ui.misc

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

/**
 * @author by linecy.
 */

class Settings(context: Context) {
  private var sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(
      context)


  /**
   * 是否使用硬解码
   */
  fun isHardCodec(): Boolean {
    return sharedPreferences.getBoolean("hard_to_decode", false)
  }

  /**
   * 是否自动播放下一画
   */
  fun isAutoPlayNext(): Boolean {
    return sharedPreferences.getBoolean("auto_play_next", true)
  }

}