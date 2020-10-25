package com.michaldrabik.ui_show.episodes

import androidx.recyclerview.widget.DiffUtil

class EpisodeListItemDiffCallback(
  private val oldList: List<EpisodeListItem>,
  private val newList: List<EpisodeListItem>
) : DiffUtil.Callback() {

  override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
    oldList[oldItemPosition].id == newList[newItemPosition].id

  override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
    val (_, _, isWatched, translation) = oldList[oldItemPosition]
    val (_, _, isWatched2, translation2) = newList[newItemPosition]

    return isWatched == isWatched2 &&
      translation?.title == translation2?.title
  }

  override fun getOldListSize() = oldList.size

  override fun getNewListSize() = newList.size
}
