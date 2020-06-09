package com.example.mentions.adapter

import com.example.mentions.MentionModel
import com.example.mentions.base.SimpleDiffAdapter

class MentionsAdapter(
  onMentionClick: (MentionModel) -> Unit
) : SimpleDiffAdapter() {

  init {
    delegatesManager.addDelegate(MentionDelegate(onMentionClick))
  }
}