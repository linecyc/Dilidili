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
import com.linecy.dilidili.data.model.Category
import com.linecy.dilidili.databinding.ItemCartoonCategoryBinding
import com.linecy.dilidili.databinding.ItemCartoonYearBinding
import com.linecy.dilidili.ui.misc.EventHandling

/**
 * @author by linecy.
 */
class CartoonCategoryAdapter(
    context: Context) : RecyclerView.Adapter<ViewHolder>() {
  private val list = ArrayList<Category>()
  private val inflater = LayoutInflater.from(context)
  private var yearsCount = 0//年份日期数据的大小

  private val typeCategory = 0
  private val typeYear = 1


  override fun getItemViewType(position: Int): Int {
    return if (position < yearsCount) {
      typeYear
    } else {
      typeCategory
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    when (viewType) {
      typeCategory -> {
        val dataBinding: ItemCartoonCategoryBinding = DataBindingUtil.inflate(inflater,
            R.layout.item_cartoon_category,
            parent, false)
        dataBinding.eventHandling = EventHandling()
        return CategoryViewHolder(dataBinding)
      }
      else -> {
        val dataBinding: ItemCartoonYearBinding = DataBindingUtil.inflate(inflater,
            R.layout.item_cartoon_year,
            parent, false)
        dataBinding.eventHandling = EventHandling()
        return YearViewHolder(dataBinding)
      }
    }
  }

  override fun getItemCount(): Int {
    return list.size
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    when (getItemViewType(position)) {
      typeCategory -> (holder as CategoryViewHolder).bindData(list[position])
      typeYear -> (holder as YearViewHolder).bindData(list[position])
    }
  }

  class CategoryViewHolder(private val dataBinding: ViewDataBinding) : ViewHolder(
      dataBinding.root) {


    fun bindData(category: Category) {
      dataBinding.setVariable(BR.itemCartoonCategory, category)
      dataBinding.executePendingBindings()
    }
  }

  class YearViewHolder(private val dataBinding: ViewDataBinding) : ViewHolder(dataBinding.root) {


    fun bindData(category: Category) {
      dataBinding.setVariable(BR.itemCartoonYear, category)
      dataBinding.executePendingBindings()
    }
  }

  /**
   * 先加载年份，在加载类型
   */
  fun refreshData(years: List<Category>?, categories: List<Category>?) {
    this.list.clear()
    yearsCount = 0
    if (years != null && years.isNotEmpty()) {
      this.list.addAll(years)
      yearsCount = years.size
    }
    if (categories != null && categories.isNotEmpty()) {
      this.list.addAll(categories)
    }

    notifyDataSetChanged()
  }
}