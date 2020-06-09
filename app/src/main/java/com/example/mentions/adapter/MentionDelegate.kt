package com.example.mentions.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mentions.MentionModel
import com.example.mentions.base.ListItem
import com.example.mentions.base.inflater
import com.example.mentions.databinding.ItemMentionBinding
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate

class MentionDelegate(
  private val onMentionClick: (MentionModel) -> Unit
) : AbsListItemAdapterDelegate<MentionModel, ListItem, MentionDelegate.VH>() {

  override fun onCreateViewHolder(parent: ViewGroup): VH =
    VH(ItemMentionBinding.inflate(parent.inflater(), parent, false))

  override fun isForViewType(item: ListItem, items: MutableList<ListItem>, position: Int): Boolean =
    item is MentionModel

  override fun onBindViewHolder(item: MentionModel, holder: VH, payloads: MutableList<Any>) {
    holder.bind(item)
  }

  inner class VH(private val binding: ItemMentionBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: MentionModel) {
      with(binding) {
        description.text = item.description
        mention.text = "@" + item.mention
        root.setOnClickListener { onMentionClick.invoke(item) }
        Glide
          .with(root)
          .load(item.pictureUrl)
          .centerCrop()
          .into(avatar)
      }
    }
  }
}