package com.linecy.dilidili.di.module

import android.app.Application
import android.content.Context
import com.linecy.dilidili.utils.ActivityManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author by linecy.
 */
@Module
class AppModule(private val application: Application) {

  @Provides
  @Singleton
  fun provideAppContext(): Context {
    return application.applicationContext
  }

  @Provides
  @Singleton
  fun provideActivityManager(): ActivityManager {
    return ActivityManager()
  }

  @Provides
  @Singleton
  fun provideApplication(): Application {
    return application
  }

}