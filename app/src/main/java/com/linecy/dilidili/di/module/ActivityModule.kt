package com.linecy.dilidili.di.module

import com.linecy.dilidili.ui.MainActivity
import com.linecy.dilidili.ui.SearchActivity
import com.linecy.dilidili.ui.play.PlayActivity
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


  @ContributesAndroidInjector
  abstract fun searchActivty(): SearchActivity
}