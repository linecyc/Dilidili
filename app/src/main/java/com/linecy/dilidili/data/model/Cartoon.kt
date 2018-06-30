package com.linecy.dilidili.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * 新番item model
 *
 * @param title 标题
 * @param coverUrl 封面
 * @param latest 当前最新集数或最后更新时间
 * @param currentTitle 当前集标题
 * @param playDetail 单集番剧播放详情url
 *
 * @author by linecy.
 */
@Parcelize
data class Cartoon(val title: String? = null, val coverUrl: String? = null,
    val latest: String? = null, val currentTitle: String? = null,
    val playDetail: String) : Parcelable