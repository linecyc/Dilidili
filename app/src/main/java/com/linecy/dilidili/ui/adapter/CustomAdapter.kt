package com.linecy.dilidili.ui.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.annotation.NonNull
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * 部分通用的adapter.
 * @author by linecy.
 */
class CustomAdapter(@NonNull private val context: Context, @NonNull layoutRes: IntArray,
    @NonNull variableIds: IntArray) : RecyclerView.Adapter<ViewHolder>() {


  private val typeHeader = 0//头
  private val typeItem = 1//列表item

  private var itemLayout: Int
  private var headerLayout: Int = -1
  private var itemVariableId: Int
  private var headerVariableId: Int = -1
  private var hasHeader: Boolean
  private var inflater: LayoutInflater

  private val list = ArrayList<Any>()
  private var headerData: Any? = null

  private var onItemClickListener: OnItemClickListener? = null
  private var onHeaderClickListener: OnHeaderClickListener? = null


  init {
    if (layoutRes.isEmpty() || variableIds.isEmpty()) {
      throw IllegalArgumentException("The layoutRes and variableId must be not null.")
    }
    itemLayout = layoutRes[0]

    itemVariableId = variableIds[0]
    if (layoutRes.size > 1) {
      headerLayout = layoutRes[1]

    }
    if (variableIds.size > 1) {
      headerVariableId = variableIds[1]
    }
    hasHeader = headerLayout != -1
    inflater = LayoutInflater.from(context)
  }


  override fun getItemViewType(position: Int): Int {
    return if (hasHeader) {
      when (position) {
        0 -> typeHeader
        else -> typeItem
      }
    } else {
      typeItem
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    return when (viewType) {
      typeHeader -> HeaderViewHolder(DataBindingUtil.inflate(inflater,
          headerLayout,
          parent, false))
      else -> CustomViewHolder(DataBindingUtil.inflate(inflater,
          itemLayout,
          parent, false))
    }
  }

  override fun getItemCount(): Int {
    return if (hasHeader) {
      list.size + 1
    } else {
      list.size
    }
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    if (holder is CustomViewHolder) {
      holder.bindData(list[if (hasHeader) position - 1 else position])
    } else if (holder is HeaderViewHolder && headerVariableId != -1) {
      holder.bindData(headerData)
    }
  }


  inner class CustomViewHolder(private val dataBinding: ViewDataBinding) : RecyclerView.ViewHolder(
      dataBinding.root) {
    init {
      itemView.setOnClickListener {
        onItemClickListener?.onItemClick(it)
      }
    }

    fun bindData(data: Any?) {
      dataBinding.setVariable(itemVariableId, data)
      dataBinding.executePendingBindings()
    }
  }


  inner class HeaderViewHolder(private val dataBinding: ViewDataBinding) : RecyclerView.ViewHolder(
      dataBinding.root) {
    init {
      itemView.setOnClickListener {
        onHeaderClickListener?.onHeaderClick(it)
      }
    }

    fun bindData(data: Any?) {
      dataBinding.setVariable(headerVariableId, data)
      dataBinding.executePendingBindings()
    }
  }


  fun refreshData(list: List<Any>?, headerData: Any? = null) {
    this.list.clear()
    if (list != null && list.isNotEmpty()) {
      this.list.addAll(list)
    }
    this.headerData = headerData
    notifyDataSetChanged()
  }

  fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
    this.onItemClickListener = onItemClickListener
  }

  fun setOnHeaderClickListener(onHeaderClickListener: OnHeaderClickListener) {
    this.onHeaderClickListener = onHeaderClickListener
  }

  interface OnItemClickListener {
    fun onItemClick(view: View)
  }

  interface OnHeaderClickListener {
    fun onHeaderClick(view: View)
  }
}