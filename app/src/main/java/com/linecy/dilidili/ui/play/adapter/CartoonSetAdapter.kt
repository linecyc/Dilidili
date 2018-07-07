package com.linecy.dilidili.ui.play.adapter

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
import com.linecy.dilidili.databinding.ItemCartoonSetBinding
import com.linecy.dilidili.databinding.LayoutCartoonSetHeaderBinding

/**
 * @author by linecy.
 */
class CartoonSetAdapter(
    private val context: Context) : RecyclerView.Adapter<ViewHolder>() {

  private val headerType = 0
  private val listType = 1

  private val list = ArrayList<Cartoon>()
  private val inflater = LayoutInflater.from(context)
  private var linstener: OnCartoonSetClickListener? = null

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    return when (viewType) {
      headerType -> {
        val dataBinding: LayoutCartoonSetHeaderBinding = DataBindingUtil.inflate(inflater,
            R.layout.layout_cartoon_set_header,
            parent, false)
        HeaderViewHolder(dataBinding)
      }
      else -> {
        val dataBinding: ItemCartoonSetBinding = DataBindingUtil.inflate(inflater,
            R.layout.item_cartoon_set,
            parent, false)
        dataBinding.onCartoonSetClickListener = linstener
        CartoonSetViewHolder(dataBinding)
      }
    }
  }


  override fun getItemViewType(position: Int): Int {
    return if (position == 0) {
      headerType
    } else {
      listType
    }
  }

  override fun getItemCount(): Int {
    return list.size
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    if (holder is CartoonSetViewHolder) {
      holder.bindData(list[position])
    }
  }

  class CartoonSetViewHolder(private val dataBinding: ViewDataBinding) : ViewHolder(
      dataBinding.root) {

    fun bindData(cartoon: Cartoon) {
      dataBinding.setVariable(BR.itemCartoonSet, cartoon)
      dataBinding.executePendingBindings()
    }
  }

  class HeaderViewHolder(private val dataBinding: ViewDataBinding) : ViewHolder(
      dataBinding.root) {

  }

  fun refreshData(list: List<Cartoon>?) {
    this.list.clear()
    if (list != null && list.isNotEmpty()) {
      this.list.addAll(list)
    }
    notifyDataSetChanged()
  }

  fun setOnCartoonSetClickListener(l: OnCartoonSetClickListener?) {
    this.linstener = l
  }

  interface OnCartoonSetClickListener {

    fun onCartoonSetClick(cartoon: Cartoon)
  }
}