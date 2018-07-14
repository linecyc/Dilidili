package com.linecy.dilidili.di.module

import com.linecy.dilidili.ui.MainActivity
import com.linecy.dilidili.ui.account.SettingsActivity
import com.linecy.dilidili.ui.cartoon.CartoonListActivity
import com.linecy.dilidili.ui.play.PlayActivity
import com.linecy.dilidili.ui.play.SerialsActivity
import com.linecy.dilidili.ui.search.SearchActivity
import com.linecy.dilidili.ui.splash.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * @author by linecy.
 */
@Module
abstract class ActivityModule {

  @ContributesAndroidInjector()
  abstract fun mainActivity(): MainActivity

  @ContributesAndroidInjector(modules = [PlayModule::class])
  abstract fun playActivity(): PlayActivity


  @ContributesAndroidInjector
  abstract fun splashActivity(): SplashActivity


  @ContributesAndroidInjector(modules = [SearchModule::class])
  abstract fun searchActivty(): SearchActivity

  @ContributesAndroidInjector(modules = [CartoonModule::class])
  abstract fun serialsActivity(): SerialsActivity

  @ContributesAndroidInjector(modules = [CartoonModule::class])
  abstract fun cartoonListActivity(): CartoonListActivity

  @ContributesAndroidInjector
  abstract fun settingsActivity(): SettingsActivity
}