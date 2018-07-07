package com.linecy.dilidili.di.module

import com.linecy.dilidili.data.datasource.CartoonDataSource
import com.linecy.dilidili.data.datasource.repository.CartoonRepository
import dagger.Module
import dagger.Provides

/**
 * @author by linecy.
 */

@Module
class CartoonModule {

  @Provides
  fun provideHomeRepository(cartoonDataSource: CartoonDataSource): CartoonRepository {
    return cartoonDataSource
  }
}
