package com.linecy.dilidili.di.component

import com.linecy.dilidili.DiliApplication
import com.linecy.dilidili.di.module.ActivityModule
import com.linecy.dilidili.di.module.AppModule
import com.linecy.dilidili.di.module.FragmentModule
import com.linecy.dilidili.ui.misc.ActivityManager
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * @author by linecy.
 */
@Singleton
@Component(
    modules = [AndroidInjectionModule::class, AndroidSupportInjectionModule::class,
      AppModule::class, ActivityModule::class, FragmentModule::class])
interface AppComponent {

  fun activityManager(): ActivityManager

  //需要注入到对应的xxApplication,不然lateinit property activityInjector has not been initialized
  fun inject(application: DiliApplication)
}