package com.linecy.dilidili.di.module

import com.linecy.dilidili.data.datasource.SearchDataSource
import com.linecy.dilidili.data.datasource.repository.SearchRepository
import dagger.Module
import dagger.Provides

/**
 * @author by linecy.
 */
@Module
class SearchModule {


  @Provides
  fun provideSearchDataSource(searchDataSource: SearchDataSource): SearchRepository {
    return searchDataSource
  }
}