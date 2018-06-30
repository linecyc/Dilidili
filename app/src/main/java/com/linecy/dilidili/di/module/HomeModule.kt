package com.linecy.dilidili.di.module

import com.linecy.dilidili.data.datasource.BannerDataSource
import com.linecy.dilidili.data.datasource.CartoonDataSource
import com.linecy.dilidili.data.datasource.repository.BannerRepository
import com.linecy.dilidili.data.datasource.repository.CartoonRepository
import dagger.Module
import dagger.Provides

/**
 * @author by linecy.
 */

@Module
class HomeModule {


  @Provides
  fun provideBannerRepository(homeDataSource: BannerDataSource): BannerRepository {
    return homeDataSource
  }

  @Provides
  fun provideCartoonRepository(cartoonDataSource: CartoonDataSource): CartoonRepository {
    return cartoonDataSource
  }
}
