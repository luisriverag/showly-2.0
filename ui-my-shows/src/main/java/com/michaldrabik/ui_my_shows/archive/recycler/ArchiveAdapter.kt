package com.michaldrabik.ui_my_shows.archive.recycler

import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.michaldrabik.ui_base.BaseAdapter
import com.michaldrabik.ui_my_shows.archive.recycler.views.ArchiveShowView

class ArchiveAdapter : BaseAdapter<ArchiveListItem>() {

  override val asyncDiffer = AsyncListDiffer(this, ArchiveDiffCallback())

  override fun setItems(newItems: List<ArchiveListItem>, notifyChange: Boolean) {
    //TODO Remove this and other after possible bug is identified.
    FirebaseCrashlytics.getInstance().setCustomKey("Adapter", "ArchiveAdapter")
    super.setItems(newItems, notifyChange)
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
    BaseViewHolder(
      ArchiveShowView(parent.context).apply {
        itemClickListener = { super.itemClickListener.invoke(it) }
        missingImageListener = { item, force -> super.missingImageListener.invoke(item, force) }
        missingTranslationListener = { super.missingTranslationListener.invoke(it) }
      }
    )

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    val item = asyncDiffer.currentList[position]
    (holder.itemView as ArchiveShowView).bind(item)
  }
}
