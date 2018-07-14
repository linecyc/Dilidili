package com.linecy.dilidili.di.module

import android.app.Application
import android.content.Context
import com.linecy.dilidili.ui.misc.ActivityManager
import com.linecy.dilidili.ui.misc.Settings
import com.linecy.module.core.rx.BackPressureRxBus
import com.linecy.module.core.rx.RxBus
import com.linecy.module.core.utils.Toaster
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

  @Provides
  @Singleton
  fun provideToaster(): Toaster {
    return Toaster(application)
  }

  @Provides
  @Singleton
  fun provideRxBus(): RxBus {
    return RxBus()
  }

  @Provides
  @Singleton
  fun provideBackPressureRxBus(): BackPressureRxBus {
    return BackPressureRxBus()
  }


  @Provides
  @Singleton
  fun provideSettings(): Settings {
    return Settings(application)
  }
}