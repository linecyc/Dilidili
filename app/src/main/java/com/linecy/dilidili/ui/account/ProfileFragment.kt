package com.linecy.dilidili.ui.account

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import com.linecy.dilidili.R
import com.linecy.dilidili.databinding.FragmentProfileBinding
import com.linecy.dilidili.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_profile.tvPlayerSetting

/**
 * @author by linecy.
 */
class ProfileFragment : BaseFragment<FragmentProfileBinding>(), OnClickListener {

  override fun layoutResId(): Int {
    return R.layout.fragment_profile
  }

  override fun onInitView(savedInstanceState: Bundle?) {
    tvPlayerSetting.setOnClickListener(this)
  }

  override fun onClick(v: View?) {
    when (v?.id) {
      R.id.tvPlayerSetting -> startActivity(Intent(context!!, SettingsActivity::class.java))
    }
  }

}