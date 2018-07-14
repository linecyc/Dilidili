package com.linecy.dilidili.ui.misc

import android.content.Intent
import android.view.View
import com.linecy.dilidili.data.model.Cartoon
import com.linecy.dilidili.data.model.Category
import com.linecy.dilidili.ui.cartoon.CartoonListActivity
import com.linecy.dilidili.ui.play.PlayActivity
import com.linecy.dilidili.ui.play.SerialsActivity

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

  fun onJumpSerialsClick(view: View, link: String?) {
    val context = view.context
    val intent = Intent(context, SerialsActivity::class.java)
    intent.putExtra(SerialsActivity.EXTRA_DATA, link)
    context.startActivity(intent)
  }

  fun onJumpListClick(view: View, category: Category) {
    val context = view.context
    val intent = Intent(context, CartoonListActivity::class.java)
    intent.putExtra(CartoonListActivity.EXTRA_DATA, category)
    context.startActivity(intent)
  }
}