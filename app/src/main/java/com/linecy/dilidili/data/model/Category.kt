package com.linecy.dilidili.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @param title 类别名称
 * @param icon 类别图标
 * @param link 类别链接
 *
 * @author by linecy.
 */
@Parcelize
data class Category(val title: String, val icon: Int, val link: String) : Parcelable