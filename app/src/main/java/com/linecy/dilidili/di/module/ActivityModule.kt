package com.linecy.dilidili.di.module

import com.linecy.dilidili.ui.home.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * @author by linecy.
 */
@Module
abstract class ActivityModule {

  @ContributesAndroidInjector()
  abstract fun mainActivity(): MainActivity

}