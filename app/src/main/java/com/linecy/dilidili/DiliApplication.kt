package com.linecy.dilidili

import android.app.Activity
import android.app.Application
import android.support.v4.app.Fragment
import com.linecy.dilidili.di.component.DaggerAppComponent
import com.linecy.dilidili.di.module.AppModule
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.HasSupportFragmentInjector
import timber.log.Timber
import javax.inject.Inject

/**
 * @author by linecy.
 */
class DiliApplication : Application(), HasActivityInjector, HasSupportFragmentInjector {

  @Inject
  lateinit var activityInjector: DispatchingAndroidInjector<Activity>

  @Inject
  lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

  override fun supportFragmentInjector() = fragmentInjector

  override fun activityInjector() = activityInjector


  override fun onCreate() {
    super.onCreate()
    if (BuildConfig.DEBUG) {
      Timber.plant(Timber.DebugTree())
    }
    DaggerAppComponent
        .builder()
        .appModule(AppModule(this))
        .build()
        .inject(this)
  }
}