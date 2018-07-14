package com.linecy.dilidili.data.presenter.cartoonCategory

import com.linecy.dilidili.R.drawable
import com.linecy.dilidili.data.model.Category
import com.linecy.dilidili.data.model.CategoryData
import com.linecy.module.core.mvp.RxPresenter
import com.linecy.module.core.rx.subscribeBy
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * @author by linecy.
 */

class CategoryPresenter @Inject constructor() : RxPresenter<CategoryView>() {


  /**
   * 加载类别
   */
  fun getCategory() {
    baseView?.showLoading()
    disposables.add(
        Observable.zip(Observable.just(createCategories()), Observable.just(createYears()),
            BiFunction<List<Category>, List<Category>, CategoryData> { categories, years ->
              CategoryData(categories, years)
            })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy({
              baseView?.hideLoading()
              baseView?.showCategory(it.categories, it.years)
            }, { baseView?.hideLoading() }))

  }

  private fun createCategories(): List<Category> {
    val list = ArrayList<Category>()
    list.add(
        Category(title = "搞笑", icon = drawable.ic_category_t71, link = "/gaoxiao/"))
    list.add(
        Category(title = "科幻", icon = drawable.ic_category_t145, link = "/kehuan/"))
    list.add(
        Category(title = "运动", icon = drawable.ic_category_t157, link = "/yundong/"))
    list.add(
        Category(title = "耽美", icon = drawable.ic_category_t156, link = "/danmei/"))
    list.add(
        Category(title = "治愈系", icon = drawable.ic_category_t75, link = "/zhiyuxi/"))
    list.add(
        Category(title = "萝莉", icon = drawable.ic_category_t30, link = "/luoli/"))
    list.add(
        Category(title = "装逼", icon = drawable.ic_category_t86, link = "/zhuangbi/"))
    list.add(
        Category(title = "游戏", icon = drawable.ic_category_t17, link = "/youxi/"))
    list.add(
        Category(title = "推理", icon = drawable.ic_category_t19, link = "/youxi/"))
    list.add(
        Category(title = "青春", icon = drawable.ic_category_t20, link = "/qingchun/"))
    list.add(
        Category(title = "恐怖", icon = drawable.ic_category_t26, link = "/kongbu/"))
    list.add(
        Category(title = "机战", icon = drawable.ic_category_t27, link = "/jizhan/"))
    list.add(
        Category(title = "热血", icon = drawable.ic_category_t96, link = "/rexue/"))
    list.add(
        Category(title = "轻小说", icon = drawable.ic_category_t28, link = "/qingxiaoshuo/"))
    list.add(
        Category(title = "冒险", icon = drawable.ic_category_t60, link = "/maoxian/"))
    list.add(
        Category(title = "后宫", icon = drawable.ic_category_t27, link = "/hougong/"))
    list.add(
        Category(title = "奇幻", icon = drawable.ic_category_t147, link = "/qihuan/"))
    list.add(
        Category(title = "童年", icon = drawable.ic_category_t153, link = "/tongnian/"))
    list.add(
        Category(title = "恋爱", icon = drawable.ic_category_t39, link = "/lianai/"))
    list.add(
        Category(title = "美少女", icon = drawable.ic_category_t82, link = "/meishaonv/"))
    list.add(
        Category(title = "励志", icon = drawable.ic_category_t154, link = "/lizhi/"))
    list.add(
        Category(title = "百合", icon = drawable.ic_category_t146, link = "/baihe/"))
    list.add(
        Category(title = "泡面番", icon = drawable.ic_category_t47, link = "/paomianfan/"))
    list.add(
        Category(title = "乙女", icon = drawable.ic_category_t31, link = "/yinv/"))
    list.add(
        Category(title = "动作", icon = drawable.ic_category_t33, link = "/dongzuo/"))

    return list
  }


  private fun createYears(): List<Category> {
    return arrayListOf(
        Category("18年7月", 0, "/anime/201807/"),
        Category("18年4月", 0, "/anime/201804/"),
        Category("2018年", 0, "/anime/2018/"),
        Category("2017年", 0, "/anime/2017/"),
        Category("2016年", 0, "/anime/2016/"),
        Category("2015年", 0, "/anime/2015/"),
        Category("2014年", 0, "/anime/2014/"),
        Category("2013年", 0, "/anime/2013/"),
        Category("2012年", 0, "/anime/2012/"),
        Category("2011年", 0, "/anime/2011/"),
        Category("2010年", 0, "/anime/2010/"),
        Category("00年代", 0, "/anime/2010xq/"),
        Category("更早", 0, "/anime/2000xqq/"))
  }

}