package com.linecy.dilidili.ui

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.linecy.dilidili.ui.widget.RollSquareView
import com.linecy.module.core.mvp.BaseView
import com.linecy.module.core.mvp.Presenter
import com.linecy.module.core.mvp.RxPresenter
import com.linecy.module.core.mvp.RxPresenterDelegate
import dagger.android.support.AndroidSupportInjection

/**
 * @author by linecy.
 */
abstract class BaseFragment<VB : ViewDataBinding> : Fragment() {


  private val rxPresenter = object : RxPresenter<BaseView>() {}
  private val rxPresenterDelegate = RxPresenterDelegate(rxPresenter)
  protected lateinit var fragmentBinding: VB
  private var dialog: AlertDialog? = null
  private lateinit var squareView: RollSquareView

  @SuppressWarnings("unchecked")
  protected fun delegatePresenter(presenter: Presenter<BaseView>, view: BaseView) {
    rxPresenterDelegate.delegate(presenter)
    rxPresenterDelegate.attach(view)
  }

  override fun onAttach(context: Context?) {
    AndroidSupportInjection.inject(this)
    super.onAttach(context)
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {
    if (layoutResId() != 0) {
      fragmentBinding = DataBindingUtil.inflate(inflater, layoutResId(), container,
          false)
      return if (fragmentBinding != null && fragmentBinding.root != null) {
        fragmentBinding.root
      } else {
        inflater?.inflate(layoutResId(), container, false)
      }
    }
    return super.onCreateView(inflater, container, savedInstanceState)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    onInitView(savedInstanceState)
  }

  override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)
    rxPresenterDelegate.saveInstanceState(outState)
  }

  override fun onViewStateRestored(savedInstanceState: Bundle?) {
    super.onViewStateRestored(savedInstanceState)
    rxPresenterDelegate.restoreInstanceState(savedInstanceState)
  }

  override fun onDestroyView() {
    super.onDestroyView()
    rxPresenterDelegate.detach()
    hideLoadingDialog()
  }

  protected abstract fun layoutResId(): Int

  protected abstract fun onInitView(savedInstanceState: Bundle?)


  protected fun showLoadingDialog() {
    loadingDialog()
  }

  protected fun hideLoadingDialog() {
    if (null != dialog && dialog?.isShowing!!) {
      dialog?.dismiss()
    }
  }

  private fun loadingDialog() {
//
//    if (dialog == null) {
//      val builder = AlertDialog.Builder(context, R.style.dialog)
//      dialog = builder.create()
//      val view = LayoutInflater.from(context).inflate(R.layout.layout_loading, null)
//      squareView = view.findViewById(R.id.rollSquareView)
//      squareView.visibility = View.VISIBLE
//      dialog?.setView(view, 200, 200, 200, 200)
//      dialog?.setCancelable(false)
//      dialog?.show()
//    } else {
//      if (null != squareView && View.VISIBLE != squareView.getVisibility()) {
//        squareView.visibility = View.VISIBLE
//      }
//      dialog?.show()
//    }
  }
}