package com.linecy.dilidili.ui

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.constraint.ConstraintLayout
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.linecy.dilidili.R
import com.linecy.dilidili.databinding.LayoutBaseBinding
import com.linecy.dilidili.ui.misc.AppContainer
import com.linecy.dilidili.ui.widget.RollSquareView
import com.linecy.module.core.mvp.BaseView
import com.linecy.module.core.mvp.Presenter
import com.linecy.module.core.mvp.RxPresenter
import com.linecy.module.core.mvp.RxPresenterDelegate
import com.linecy.module.core.utils.Toaster
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.layout_base.appContainer
import javax.inject.Inject

/**
 * @author by linecy.
 */
abstract class BaseActivity<VB : ViewDataBinding> : AppCompatActivity(), OnClickListener {

  @Inject
  lateinit var toaster: Toaster

  private val rxPresenter = object : RxPresenter<BaseView>() {}
  private val rxPresenterDelegate = RxPresenterDelegate(rxPresenter)
  protected var mDataBinding: VB? = null
  private var mBaseBinding: LayoutBaseBinding? = null
  private var mToolbar: ConstraintLayout? = null
  private var mTvTitle: TextView? = null
  private var mIbHomeAsUp: ImageButton? = null

  private var dialog: AlertDialog? = null
  private lateinit var squareView: RollSquareView

  @Suppress("UNCHECKED_CAST")
  protected fun delegatePresenter(presenter: Presenter<*>, view: BaseView) {
    rxPresenterDelegate.delegate(presenter as Presenter<BaseView>)
    rxPresenterDelegate.attach(view)
  }

  protected abstract fun layoutResId(): Int

  protected abstract fun onInitView(savedInstanceState: Bundle?)


  override fun onCreate(savedInstanceState: Bundle?) {
    AndroidInjection.inject(this)
    super.onCreate(savedInstanceState)
    setContentView(layoutResId())
    onInitView(savedInstanceState)
  }

  override fun setContentView(layoutResID: Int) {
    val app = AppContainer.DEFAULT
    val rootView = app.bind(this)

    mBaseBinding = DataBindingUtil.inflate(layoutInflater, R.layout.layout_base, rootView, true)
    if (0 != layoutResID) {
      mDataBinding = DataBindingUtil.inflate(layoutInflater, layoutResID, null, false)

      // content
      if (mDataBinding == null) {
        layoutInflater.inflate(layoutResID, appContainer)
      } else {
        mBaseBinding?.appContainer?.addView(mDataBinding?.root)
      }
    }
    // title
    mToolbar = mBaseBinding?.root?.findViewById(R.id.containerToolBar)
    mIbHomeAsUp = mBaseBinding?.root?.findViewById(R.id.containerUp)
    mTvTitle = mBaseBinding?.root?.findViewById(R.id.containerTitle)

    mIbHomeAsUp?.setOnClickListener(this)

  }

  override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)
    rxPresenterDelegate.saveInstanceState(outState)
  }

  override fun onRestoreInstanceState(savedInstanceState: Bundle) {
    super.onRestoreInstanceState(savedInstanceState)
    rxPresenterDelegate.restoreInstanceState(savedInstanceState)
  }

  override fun onDestroy() {
    super.onDestroy()
    rxPresenterDelegate.detach()
    hideLoadingDialog()
  }


  override fun onClick(p0: View?) {
    when (p0?.id) {
      R.id.containerUp -> finish()

    }
  }

  protected fun hideToolBar() {
    mToolbar?.visibility = View.GONE
  }

  protected fun showToolBar() {
    mToolbar?.visibility = View.VISIBLE
  }

  protected fun setDisplayHomeAsUp(show: Boolean) {
    mIbHomeAsUp?.visibility = if (show) View.VISIBLE else View.GONE
  }

  protected fun setToolbarTitle(title: CharSequence) {
    mTvTitle?.text = title
  }

  protected fun setToolbarTitle(@StringRes titleRes: Int) {
    mTvTitle?.setText(titleRes)
  }

  protected fun showToast(string: String) {
    Toast.makeText(this, string, Toast.LENGTH_SHORT).show()
  }

  protected fun showLoadingDialog() {
    loadingDialog()
  }

  protected fun hideLoadingDialog() {
    if (dialog != null && dialog?.isShowing!!) {
      dialog?.dismiss()
    }
  }

  private fun loadingDialog() {
    if (!this.isFinishing) {
      if (dialog == null) {
        val builder = AlertDialog.Builder(this, R.style.dialog)
        dialog = builder.create()
        val view = LayoutInflater.from(this).inflate(R.layout.layout_loading, null)
        squareView = view.findViewById(R.id.rollSquareView)
        squareView.visibility = View.VISIBLE
        dialog?.setView(view, 200, 0, 200, 0)
        dialog?.setCancelable(false)
        dialog?.show()
      } else {
        if (View.VISIBLE != squareView.visibility) {
          squareView.visibility = View.VISIBLE
        }
        dialog?.show()
      }
    }
  }

}
