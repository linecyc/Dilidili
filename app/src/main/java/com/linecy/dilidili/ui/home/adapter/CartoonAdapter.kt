package com.linecy.dilidili.ui.home.adapter

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
import com.linecy.dilidili.databinding.ItemCartoonBinding
import com.linecy.dilidili.ui.home.adapter.CartoonAdapter.CartoonViewHolder
import com.linecy.dilidili.ui.misc.EventHandling

/**
 * @author by linecy.
 */
class CartoonAdapter(private val context: Context) : RecyclerView.Adapter<CartoonViewHolder>() {
  private val list = ArrayList<Cartoon>()
  private val inflater = LayoutInflater.from(context)


  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartoonViewHolder {
    val dataBinding: ItemCartoonBinding = DataBindingUtil.inflate(inflater, R.layout.item_cartoon,
        parent, false)
    dataBinding.eventHandling = EventHandling()
    return CartoonViewHolder(dataBinding)
  }

  override fun getItemCount(): Int {
    return list.size
  }

  override fun onBindViewHolder(holder: CartoonViewHolder, position: Int) {
    holder.bindData(list[position])
  }

  class CartoonViewHolder(private val dataBinding: ViewDataBinding) : ViewHolder(dataBinding.root) {


    fun bindData(cartoon: Cartoon) {
      dataBinding.setVariable(BR.itemCartoon, cartoon)
      dataBinding.executePendingBindings()
    }
  }

  fun refreshData(list: List<Cartoon>?) {
    this.list.clear()
    if (list != null && list.isNotEmpty()) {
      this.list.addAll(list)
    }
    notifyDataSetChanged()
  }
}