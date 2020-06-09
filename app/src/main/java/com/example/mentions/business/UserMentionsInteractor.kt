package com.example.mentions.business

import com.example.mentions.MentionModel
import kotlinx.coroutines.flow.Flow


interface UserMentionsInteractor {
    fun data(threadId: Long = 0): Flow<List<MentionModel>>
    fun filterMentions(mentions: List<MentionModel>, query: String): List<MentionModel>
}