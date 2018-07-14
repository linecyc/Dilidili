package com.linecy.dilidili.di.module

import com.linecy.dilidili.ui.account.PlayerSettingFragment
import com.linecy.dilidili.ui.account.ProfileFragment
import com.linecy.dilidili.ui.cartoon.CartoonCategoryFragment
import com.linecy.dilidili.ui.cartoon.CartoonFragment
import com.linecy.dilidili.ui.cartoon.CartoonWeekFragment
import com.linecy.dilidili.ui.home.HomeFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * @author by linecy.
 */
@Module
abstract class FragmentModule {

  @ContributesAndroidInjector
  abstract fun cartoonFragment(): CartoonFragment

  @ContributesAndroidInjector(modules = [CartoonModule::class])
  abstract fun cartoonTimeFragment(): CartoonWeekFragment

  @ContributesAndroidInjector
  abstract fun cartoonCategoryFragment(): CartoonCategoryFragment

  @ContributesAndroidInjector(modules = [HomeModule::class])
  abstract fun homeFragment(): HomeFragment

  @ContributesAndroidInjector
  abstract fun profileFragment(): ProfileFragment

  @ContributesAndroidInjector
  abstract fun playerSettingFragment(): PlayerSettingFragment
}