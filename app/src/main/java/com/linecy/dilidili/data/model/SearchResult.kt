package com.linecy.dilidili.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @author by linecy.
 */

@Parcelize
data class SearchResult(val resultCount: String?, val list: List<ResultItem>?, val currentPage: Int,
    val totalPage: Int) : Parcelable