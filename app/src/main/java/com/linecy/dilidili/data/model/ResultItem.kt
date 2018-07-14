package com.linecy.dilidili.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * 新番item model
 *
 * @param title 标题
 * @param coverUrl 封面
 * @param detail 描述
 * @param link 链接
 *
 * @author by linecy.
 */
@Parcelize
data class ResultItem(val title: String? = null, val coverUrl: String? = null,
    val detail: String?, val link: String?) : Parcelable