package com.linecy.dilidili.data.datasource.repository

import io.reactivex.Observable
import org.jsoup.nodes.Document

/**
 * @author by linecy.
 */
interface SearchRepository {

  fun search(content: String): Observable<Document>

  fun loadMoreResult(content: String, next: Int): Observable<Document>

}