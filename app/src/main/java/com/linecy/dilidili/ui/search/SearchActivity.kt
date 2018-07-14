package com.linecy.dilidili.ui.search

import android.content.Context
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.TextView.OnEditorActionListener
import com.linecy.dilidili.R
import com.linecy.dilidili.data.model.SearchResult
import com.linecy.dilidili.data.presenter.search.SearchPresenter
import com.linecy.dilidili.data.presenter.search.SearchView
import com.linecy.dilidili.ui.BaseActivity
import com.linecy.dilidili.ui.misc.ViewContainer.OnReloadCallBack
import com.linecy.dilidili.ui.search.adapter.SearchAdapter
import kotlinx.android.synthetic.main.activity_search.recyclerView
import kotlinx.android.synthetic.main.activity_search.viewContainer
import kotlinx.android.synthetic.main.fragment_home.swipeLayout
import kotlinx.android.synthetic.main.layout_search.etSearch
import kotlinx.android.synthetic.main.layout_search.ivClose
import kotlinx.android.synthetic.main.layout_search.ivSearch
import javax.inject.Inject


/**
 * @author by linecy.
 */
class SearchActivity : BaseActivity<ViewDataBinding>(), SearchView,
    OnRefreshListener, OnReloadCallBack {

  @Inject
  lateinit var searchPresenter: SearchPresenter

  private lateinit var adapter: SearchAdapter

  override fun layoutResId(): Int {
    return R.layout.activity_search
  }

  override fun onInitView(savedInstanceState: Bundle?) {
    delegatePresenter(searchPresenter, this)
    hideToolBar()
    swipeLayout.setColorSchemeResources(R.color.colorPrimary)
    swipeLayout.setOnRefreshListener(this)
    viewContainer.setOnReloadCallBack(this)
    ivClose.setOnClickListener(this)
    ivSearch.setOnClickListener(this)
    ivSearch.setImageResource(R.drawable.ic_action_back)
    etSearch.addTextChangedListener(CustomTextWatcher())
    etSearch.setOnEditorActionListener(EditActionListener())

    val manager = LinearLayoutManager(this)
    recyclerView.layoutManager = manager
    adapter = SearchAdapter(this)
    recyclerView.adapter = adapter
    recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
      override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (adapter.itemCount - 1 == manager.findLastVisibleItemPosition() && !searchPresenter.isLoading) {
          if (adapter.hasMore()) {
            searchPresenter.loadMore(adapter.getNextPage())
          } else {
            adapter.notifyItemChanged(adapter.itemCount - 1)
          }
        }
      }
    })

    viewContainer.setDisplayedChildId(R.id.swipeLayout)
  }


  override fun onClick(p0: View?) {
    super.onClick(p0)
    when (p0?.id) {
      R.id.ivClose -> etSearch.text = null
      R.id.ivSearch -> finish()
    }
  }

  override fun showLoading() {
    if (!swipeLayout.isRefreshing) {
      showLoadingDialog()
    }
  }

  override fun hideLoading() {
    hideLoadingDialog()
    swipeLayout.isRefreshing = false
  }

  override fun onReload() {
    onRefresh()
  }

  override fun onRefresh() {
    val content = etSearch.text.toString()
    if (!TextUtils.isEmpty(content)) {
      searchPresenter.search(content)
    } else {
      viewContainer.setDisplayedChildId(R.id.swipeLayout)
    }
  }

  override fun showResult(result: SearchResult) {
    if (result.totalPage == 0) {
      viewContainer.setDisplayedChildId(R.id.empty)
    } else {
      viewContainer.setDisplayedChildId(R.id.swipeLayout)
    }
    adapter.refreshData(result)

  }

  override fun showMore(result: SearchResult) {
    adapter.addMore(result)
  }

  override fun showError() {
    viewContainer.setDisplayedChildId(R.id.error)
  }


  inner class EditActionListener : OnEditorActionListener {
    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
      if (actionId == EditorInfo.IME_ACTION_SEARCH) {
        val content = etSearch.text.toString()
        if (!TextUtils.isEmpty(content)) {
          viewContainer.setDisplayedChildId(R.id.layoutNull)
          searchPresenter.search(content)
          val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
          imm.hideSoftInputFromWindow(currentFocus.windowToken,
              InputMethodManager.HIDE_NOT_ALWAYS)
          return false
        }
      }
      return true
    }

  }

  inner class CustomTextWatcher : TextWatcher {
    override fun afterTextChanged(s: Editable?) {}

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
      ivClose.visibility = if (s == null || s.isEmpty()) View.GONE else View.VISIBLE
    }
  }
}