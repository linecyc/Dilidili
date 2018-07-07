package com.linecy.dilidili.ui.misc

import android.content.Intent
import android.view.View
import com.linecy.dilidili.data.model.Cartoon
import com.linecy.dilidili.data.model.Category
import com.linecy.dilidili.ui.play.PlayActivity

/**
 * @author by linecy.
 */
class EventHandling {

  fun onCartoonClick(view: View, cartoon: Cartoon?) {
    val context = view.context
    val intent = Intent(context, PlayActivity::class.java)
    intent.putExtra(PlayActivity.EXTRA_CARTOON, cartoon)
    context.startActivity(intent)
  }


  fun onCartoonCategoryClick(view: View, category: Category?) {
    //val context = view.context
    //val intent = Intent(context, PlayActivity::class.java)
    //intent.putExtra(PlayActivity.EXTRA_CARTOON, category)
    //context.startActivity(intent)
  }
}