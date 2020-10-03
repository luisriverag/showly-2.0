package com.michaldrabik.showly2.ui.show.actors

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy.DATA
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.michaldrabik.common.Config.IMAGE_FADE_DURATION_MS
import com.michaldrabik.common.Config.TVDB_IMAGE_BASE_BANNERS_URL
import com.michaldrabik.showly2.R
import com.michaldrabik.showly2.utilities.extensions.dimenToPx
import com.michaldrabik.showly2.utilities.extensions.gone
import com.michaldrabik.showly2.utilities.extensions.visible
import com.michaldrabik.showly2.utilities.extensions.withFailListener
import com.michaldrabik.ui_model.Actor
import kotlinx.android.synthetic.main.view_actor.view.*

class ActorView : FrameLayout {

  constructor(context: Context) : super(context)
  constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
  constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

  private val cornerRadius by lazy { context.dimenToPx(R.dimen.actorTileCorner) }

  init {
    inflate(context, R.layout.view_actor, this)
    layoutParams = LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
  }

  fun bind(item: Actor, clickListener: (Actor) -> Unit) {
    clear()
    tag = item.id
    setOnClickListener {
      if (item.image.isNotBlank()) clickListener(item)
    }
    actorName.text = item.name.split(" ").joinToString("\n")
    loadImage(item)
  }

  private fun loadImage(actor: Actor) {
    if (actor.image.isBlank()) {
      actorPlaceholder.visible()
      actorRoot.setBackgroundResource(R.drawable.bg_show_view_placeholder)
      return
    }

    Glide.with(this)
      .load("$TVDB_IMAGE_BASE_BANNERS_URL${actor.image}")
      .diskCacheStrategy(DATA)
      .transform(CenterCrop(), RoundedCorners(cornerRadius))
      .transition(withCrossFade(IMAGE_FADE_DURATION_MS))
      .withFailListener {
        actorPlaceholder.visible()
        actorRoot.setBackgroundResource(R.drawable.bg_show_view_placeholder)
      }
      .into(actorImage)
  }

  private fun clear() {
    actorPlaceholder.gone()
    actorRoot.setBackgroundResource(0)
    Glide.with(this).clear(actorImage)
  }
}
