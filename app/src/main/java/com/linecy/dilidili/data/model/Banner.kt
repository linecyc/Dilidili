package com.linecy.dilidili.data.model

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @author by linecy.
 */
//@Parcelize
data class Banner(val srcUrl: String, val detail: String?, val linkUrl: String) : Parcelable {
  constructor(source: Parcel) : this(
      source.readString(),
      source.readString(),
      source.readString()
  )

  override fun describeContents() = 0

  override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
    writeString(srcUrl)
    writeString(detail)
    writeString(linkUrl)
  }

  companion object {
    @JvmField
    val CREATOR: Parcelable.Creator<Banner> = object : Parcelable.Creator<Banner> {
      override fun createFromParcel(source: Parcel): Banner = Banner(source)
      override fun newArray(size: Int): Array<Banner?> = arrayOfNulls(size)
    }
  }
}