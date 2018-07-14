package com.linecy.dilidili.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * 用于展示频道页
 * @author by linecy.
 */
@Parcelize
data class CategoryData(val categories: List<Category>, val years: List<Category>) : Parcelable