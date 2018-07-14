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
import com.linecy.dilidili.ui.play.adapter.CartoonSetAdapter.CartoonSetViewHolder
import kotlinx.android.synthetic.main.item_cartoon_set.view.tvLabel
import kotlinx.android.synthetic.main.item_cartoon_set.view.tvTitle

/**
 * @author by linecy.
 */
class CartoonSetAdapter(context: Context) : RecyclerView.Adapter<CartoonSetViewHolder>() {

  private val headerType = 0
  private val listType = 1

  private val list = ArrayList<Cartoon>()
  private val inflater = LayoutInflater.from(context)
  private var linstener: OnCartoonSetClickListener? = null
  private var current: String? = null//当前position的链接
  private var nextLink: String? = null//下一话链接

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartoonSetViewHolder {
    val dataBinding: ItemCartoonSetBinding = DataBindingUtil.inflate(inflater,
        R.layout.item_cartoon_set,
        parent, false)
    return CartoonSetViewHolder(dataBinding)
  }

  override fun getItemCount(): Int {
    return list.size
  }

  override fun onBindViewHolder(holder: CartoonSetViewHolder, position: Int) {
    holder.bindData(list[position], position)
  }

  inner class CartoonSetViewHolder(private val dataBinding: ViewDataBinding) : ViewHolder(
      dataBinding.root) {
    private lateinit var cartoon: Cartoon
    private var index: Int = 0

    init {
      dataBinding.root.setOnClickListener {
        current = cartoon.playDetail
        linstener?.onCartoonSetClick(cartoon, index)
        notifyDataSetChanged()//记录两个的位置用itemChanged更好
        dataBinding.root.isEnabled = false //选中后不可再次点击
      }
    }

    fun bindData(cartoon: Cartoon, position: Int) {
      this.cartoon = cartoon
      this.index = position
      if (current.equals(cartoon.playDetail)) {
        itemView.isSelected = true
        if (position < list.size - 1) {
          nextLink = list[position + 1].playDetail
        }
        dataBinding.root.tvLabel.isSelected = true
        dataBinding.root.tvTitle.isSelected = true
        dataBinding.root.isEnabled = false
      } else {
        itemView.isSelected = false
        dataBinding.root.tvLabel.isSelected = false
        dataBinding.root.tvTitle.isSelected = false
        dataBinding.root.isEnabled = true
      }
      dataBinding.setVariable(BR.itemCartoonSet, cartoon)
      dataBinding.executePendingBindings()
    }
  }

  fun refreshData(list: List<Cartoon>?, currentLink: String?) {
    this.list.clear()
    this.current = currentLink
    if (list != null && list.isNotEmpty()) {
      this.list.addAll(list)
    }
    notifyDataSetChanged()
  }


  fun getNextLink(): String? {
    return nextLink
  }

  fun setOnCartoonSetClickListener(l: OnCartoonSetClickListener?) {
    this.linstener = l
  }

  interface OnCartoonSetClickListener {

    fun onCartoonSetClick(cartoon: Cartoon, position: Int)
  }
}