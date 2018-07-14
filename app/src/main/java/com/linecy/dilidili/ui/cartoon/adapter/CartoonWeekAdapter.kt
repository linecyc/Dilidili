package com.linecy.dilidili.ui.cartoon.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.LayoutInflater
import android.view.ViewGroup
import com.linecy.dilidili.BR
import com.linecy.dilidili.R
import com.linecy.dilidili.data.model.Cartoon
import com.linecy.dilidili.databinding.ItemCartoonWeekBinding
import com.linecy.dilidili.ui.cartoon.adapter.CartoonWeekAdapter.CartoonViewHolder
import com.linecy.dilidili.ui.misc.EventHandling
import java.util.Calendar
import java.util.Locale

/**
 * @author by linecy.
 */
class CartoonWeekAdapter(context: Context) : RecyclerView.Adapter<CartoonViewHolder>() {

  private val list = ArrayList<ArrayList<Cartoon>>(7)
  private val inflater = LayoutInflater.from(context)

  private val defaultTime: Int
  private var current: Int = 0//默认加载"今天"

  init {
    val c = Calendar.getInstance(Locale.CHINA)
    defaultTime = formatDay(c.get(Calendar.DAY_OF_WEEK))
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartoonViewHolder {
    val dataBinding: ItemCartoonWeekBinding = DataBindingUtil.inflate(inflater,
        R.layout.item_cartoon_week,
        parent, false)
    dataBinding.eventHandling = EventHandling()
    return CartoonViewHolder(dataBinding)
  }

  //如果数组越界，则html解析的数据异常？
  override fun getItemCount(): Int {
    return if (list.size > 0 && null != list[current]) {
      list[current].size
    } else {
      0
    }
  }

  override fun onBindViewHolder(holder: CartoonViewHolder, position: Int) {
    holder.bindData(list[current][position])
  }

  class CartoonViewHolder(private val dataBinding: ViewDataBinding) : ViewHolder(dataBinding.root) {


    fun bindData(cartoon: Cartoon) {
      dataBinding.setVariable(BR.itemCartoonWeek, cartoon)
      dataBinding.executePendingBindings()
    }
  }

  fun refreshData(list: ArrayList<ArrayList<Cartoon>>?, position: Int) {
    this.list.clear()
    this.current = position
    if (list != null && list.isNotEmpty()) {
      this.list.addAll(list)
    }
    notifyDataSetChanged()
  }

  fun refreshData(position: Int = 0) {
    val pos = formatDay(position)
    if (current != pos) {
      this.current = pos
      notifyDataSetChanged()
    }
  }

  /**
   * 每周星期是7，1,2,3,4,5,6
   * 而从html解析的数据是1,2,3,4,5,6,7
   * 格式转换一下
   *
   * 如果数据不准确，重新解析，取每天的class
   */
  private fun formatDay(position: Int): Int {
    if (position > 7 || position < 0) {
      throw IllegalArgumentException("The week day must be start 1 to 7,and ")
    }
    return when (position) {
      1 -> 6
      else -> position - 2
    }
  }
}