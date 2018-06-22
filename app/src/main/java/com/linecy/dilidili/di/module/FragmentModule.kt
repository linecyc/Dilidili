package com.linecy.dilidili.di.module

import com.linecy.dilidili.ui.home.CartoonFragment
import com.linecy.dilidili.ui.home.HomeFragment
import com.linecy.dilidili.ui.home.ProfileFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * @author by linecy.
 */
@Module
abstract class FragmentModule {

  @ContributesAndroidInjector
  abstract fun cartoonFragment(): CartoonFragment

  @ContributesAndroidInjector(modules = [HomeModule::class])
  abstract fun homeFragment(): HomeFragment

  @ContributesAndroidInjector
  abstract fun profileFragment(): ProfileFragment
}