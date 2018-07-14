package com.linecy.dilidili.ui.search.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.linecy.dilidili.BR
import com.linecy.dilidili.R
import com.linecy.dilidili.data.model.ResultItem
import com.linecy.dilidili.data.model.SearchResult
import com.linecy.dilidili.databinding.ItemSearchResultBinding
import com.linecy.dilidili.ui.misc.EventHandling
import kotlinx.android.synthetic.main.layout_footer.view.progressBar

/**
 * @author by linecy.
 */
class SearchAdapter(context: Context) : RecyclerView.Adapter<ViewHolder>() {

  private val inflater = LayoutInflater.from(context)


  private val typeHeader = 0
  private val typeList = 1
  private val typeFooter = 2

  private var currentPage = 0
  private var totalPage = 0

  private val list = ArrayList<ResultItem>()
  private var resultCount: String? = null

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    return when (viewType) {
      typeHeader -> HeaderViewHolder(
          DataBindingUtil.inflate(inflater, R.layout.layout_search_header, parent, false))

      typeFooter -> FooterViewHolder(
          DataBindingUtil.inflate(inflater, R.layout.layout_footer, parent, false))

      else -> {
        val binding: ItemSearchResultBinding = DataBindingUtil.inflate(inflater,
            R.layout.item_search_result, parent, false)
        binding.eventHandling = EventHandling()
        SearchViewHolder(binding)
      }
    }
  }


  override fun getItemViewType(position: Int): Int {
    return when (position) {
      0 -> typeHeader
      itemCount - 1 -> typeFooter
      else -> typeList
    }
  }

  override fun getItemCount(): Int {
    return if (list.size > 0) {
      list.size + 2
    } else {
      0
    }
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    when (getItemViewType(position)) {
      typeHeader -> (holder as HeaderViewHolder).bindData(resultCount)
      typeFooter -> (holder as FooterViewHolder).bindData()
      typeList -> (holder as SearchViewHolder).bindData(list[position - 1])
    }
  }

  class HeaderViewHolder(private val dataBinding: ViewDataBinding) : ViewHolder(dataBinding.root) {
    fun bindData(resultCount: String?) {
      dataBinding.setVariable(BR.searchHeader, resultCount)
      dataBinding.executePendingBindings()
    }
  }

  inner class FooterViewHolder(private val dataBinding: ViewDataBinding) : ViewHolder(
      dataBinding.root) {

    fun bindData() {
      if (hasMore()) {
        dataBinding.root.progressBar.visibility = View.VISIBLE
        dataBinding.setVariable(BR.footer, dataBinding.root.context.getString(
            R.string.loading))
      } else {
        dataBinding.root.progressBar.visibility = View.GONE
        dataBinding.setVariable(BR.footer, dataBinding.root.context.getString(R.string.no_more))
      }
      dataBinding.executePendingBindings()
    }
  }

  class SearchViewHolder(private val dataBinding: ViewDataBinding) : ViewHolder(dataBinding.root) {
    fun bindData(resultItem: ResultItem) {
      dataBinding.setVariable(BR.resultItem, resultItem)
      dataBinding.executePendingBindings()
    }
  }

  fun refreshData(searchResult: SearchResult?) {
    this.list.clear()
    if (searchResult?.list != null && searchResult.list.isNotEmpty()) {
      this.list.addAll(searchResult.list)
    }
    this.totalPage = searchResult?.totalPage ?: totalPage
    this.currentPage = searchResult?.currentPage ?: currentPage
    this.resultCount = searchResult?.resultCount
    notifyDataSetChanged()
  }


  fun addMore(searchResult: SearchResult?) {
    if (searchResult?.list != null && searchResult.list.isNotEmpty()) {
      this.list.addAll(searchResult.list)
      notifyItemRangeInserted(itemCount - searchResult.list.size, searchResult.list.size)
    }
    this.currentPage = searchResult?.currentPage ?: currentPage
    this.resultCount = searchResult?.resultCount

  }

  fun hasMore(): Boolean {
    return this.currentPage + 1 < this.totalPage
  }

  fun getNextPage(): Int {
    return this.currentPage + 1
  }
}