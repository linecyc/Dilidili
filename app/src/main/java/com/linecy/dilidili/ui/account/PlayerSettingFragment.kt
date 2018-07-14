package com.linecy.dilidili.ui.account

import android.os.Bundle
import android.preference.PreferenceFragment
import com.linecy.dilidili.R

/**
 * @author by linecy.
 */
class PlayerSettingFragment : PreferenceFragment() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    this.addPreferencesFromResource(R.xml.player_preference)
  }
}