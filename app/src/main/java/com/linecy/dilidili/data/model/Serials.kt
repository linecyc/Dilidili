package com.linecy.dilidili.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * 番剧剧集
 * @param title 当季标题
 * @param coverUrl 当季封面
 * @param detail 当季描述
 * @param link 当季链接
 * @param location 地区
 * @param year 年代
 * @param labels 标签
 * @param watchingFocus 当季看点
 * @param state 状态
 * @param cartoons 当季包含的集数
 * @param season 第几季
 *
 * @author by linecy.
 */
@Parcelize
data class Serials(val title: String? = null, val coverUrl: String? = null,
    val detail: String? = null, val link: String? = null, val location: Label? = null,
    val year: Label? = null, val labels: List<Label>? = null, val watchingFocus: String? = null,
    val state: String? = null, val cartoons: List<Cartoon>? = null,
    val season: String = "当前季") : Parcelable