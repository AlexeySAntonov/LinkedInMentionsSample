package com.example.mentions

import com.example.mentions.base.ListItem
import com.linkedin.android.spyglass.mentions.Mentionable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MentionModel(
    val id: Long,
    val pictureUrl: String?,
    val mention: String,
    val description: String
) : Mentionable, ListItem {

    override fun getSuggestibleId(): Int = id.toInt()

    override fun getSuggestiblePrimaryText(): String = "@$mention"

    override fun getTextForDisplayMode(mode: Mentionable.MentionDisplayMode) = suggestiblePrimaryText

    override fun getDeleteStyle() = Mentionable.MentionDeleteStyle.FULL_DELETE

    override fun itemId(): Long = id
}