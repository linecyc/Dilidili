package com.linecy.dilidili.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @author by linecy.
 *
 * @param listSerial 番剧所有季数
 * @param recommend1 推荐1
 * @param recommend2 推荐2
 *
 */

@Parcelize
data class SerialsDetail(val listSerial: List<Serials>,
    val recommend1: List<Label>?, val recommend2: List<Cartoon>?) : Parcelable