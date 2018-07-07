package com.linecy.dilidili.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @author by linecy.
 */
@Parcelize
data class CategoryData(val categories: List<Category>, val years: List<Category>) : Parcelable