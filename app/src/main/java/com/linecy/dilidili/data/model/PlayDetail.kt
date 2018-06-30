package com.linecy.dilidili.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


/**
 * 播放的番剧单集model
 *
 * @param title 标题
 * @param playUrl 播放地址
 * @param coverUrl 封面
 * @param current 当前集数
 * @param updateTime 最近更新时间
 * @param detail 当前番剧详情url
 * @param cartoonList 当前番集数列表
 *
 * @author by linecy.
 */
@Parcelize
data class PlayDetail(val title: String, val playUrl: String?, val coverUrl: String?,
    val current: String,
    val updateTime: String, val detail: String, val cartoonList: List<Cartoon>) : Parcelable