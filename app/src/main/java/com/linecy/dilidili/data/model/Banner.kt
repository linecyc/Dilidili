package com.linecy.dilidili.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @author by linecy.
 */
@Parcelize
data class Banner(val srcUrl: String, val title: String?, val linkUrl: String) : Parcelable