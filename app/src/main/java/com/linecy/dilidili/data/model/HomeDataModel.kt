package com.linecy.dilidili.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @author by linecy.
 */
@Parcelize
data class HomeDataModel(val banners: List<Banner>,
    val cartoons: List<Cartoon>) : Parcelable