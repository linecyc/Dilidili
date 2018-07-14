package com.linecy.dilidili.ui.play.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.LayoutInflater
import android.view.ViewGroup
import com.linecy.dilidili.BR
import com.linecy.dilidili.R
import com.linecy.dilidili.data.model.Cartoon
import com.linecy.dilidili.data.model.Label
import com.linecy.dilidili.databinding.ItemSerialsDetailBinding
import com.linecy.dilidili.databinding.LayoutRecyclerViewBinding
import com.linecy.dilidili.ui.adapter.CustomAdapter

/**
 * @author by linecy.
 */
class SerialsAdapter(context: Context) : RecyclerView.Adapter<ViewHolder>() {

  private val typeHeader = 0
  private val typeList = 1

  private var hasHeader = false

  private val list = ArrayList<Cartoon>()
  private val headerList = ArrayList<Label>()
  private val inflater = LayoutInflater.from(context)


  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    return when (viewType) {
      typeHeader -> {
        val dataBinding: LayoutRecyclerViewBinding = DataBindingUtil.inflate(inflater,
            R.layout.layout_recycler_view,
            parent, false)
        SerialsViewHolder(dataBinding)
      }
      else -> {
        val dataBinding: ItemSerialsDetailBinding = DataBindingUtil.inflate(inflater,
            R.layout.item_serials_detail,
            parent, false)
        ListViewHolder(dataBinding)
      }
    }
  }


  override fun getItemViewType(position: Int): Int {
    return if (hasHeader && position == 0) {
      typeHeader
    } else {
      typeList
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
    when (getItemViewType(position)) {
      typeHeader -> (holder as SerialsViewHolder).bindData(headerList)
      else -> if (hasHeader) {
        (holder as ListViewHolder).bindData(list[position - 1])
      } else {
        (holder as ListViewHolder).bindData(list[position])
      }
    }
  }

  class SerialsViewHolder(dataBinding: LayoutRecyclerViewBinding) : ViewHolder(
      dataBinding.root) {
    private var adapter: CustomAdapter

    init {
      val manager = LinearLayoutManager(itemView.context)
      manager.orientation = RecyclerView.HORIZONTAL
      dataBinding.recyclerView.layoutManager = manager
      adapter = CustomAdapter(itemView.context, intArrayOf(R.layout.item_serials_labels),
          intArrayOf(BR.itemSerialSeason))
      dataBinding.recyclerView.adapter = adapter
    }

    fun bindData(label: List<Label>?) {
      adapter.refreshData(label, null)
    }
  }

  class ListViewHolder(private val dataBinding: ViewDataBinding) : ViewHolder(dataBinding.root) {
    fun bindData(cartoon: Cartoon) {
      dataBinding.setVariable(BR.itemSerialsDetail, cartoon)
      dataBinding.executePendingBindings()
    }
  }


  fun refreshData(list: List<Cartoon>?, labels: List<Label>? = null) {
    this.list.clear()
    this.headerList.clear()
    if (list != null && list.isNotEmpty()) {
      this.list.addAll(list)
    }

    hasHeader = if (labels != null && labels.isNotEmpty()) {
      this.headerList.addAll(labels)
      true
    } else {
      false
    }
    notifyDataSetChanged()
  }
}