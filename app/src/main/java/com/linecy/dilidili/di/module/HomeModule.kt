package com.linecy.dilidili.di.module

import com.linecy.dilidili.data.datasource.HomeDataSource
import com.linecy.dilidili.data.datasource.HomeRepository
import dagger.Module
import dagger.Provides

/**
 * @author by linecy.
 */

@Module
class HomeModule {


  @Provides
  fun provideHomeRepository(homeDataSource: HomeDataSource): HomeRepository {
    return homeDataSource
  }
}
