package com.linecy.dilidili.ui.account

import android.databinding.ViewDataBinding
import android.os.Bundle
import com.linecy.dilidili.R
import com.linecy.dilidili.ui.BaseActivity

/**
 * @author by linecy.
 */
class SettingsActivity : BaseActivity<ViewDataBinding>() {
  override fun layoutResId(): Int {
    return 0
  }

  override fun onInitView(savedInstanceState: Bundle?) {
    setToolbarTitle(R.string.setting)
    setDisplayHomeAsUp(true)
    this.fragmentManager.beginTransaction()
        .replace(R.id.appContainer, PlayerSettingFragment())
        .commit()
  }

}