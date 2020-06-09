package com.example.mentions.business

import com.example.mentions.MentionModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserMentionsInteractorImpl : UserMentionsInteractor {

    override fun data(threadId: Long): Flow<List<MentionModel>> = flow {
        emit(
            listOf(
                MentionModel(
                    id = ALL_MENTION_ID,
                    pictureUrl = "https://d3gopequ36f0ms.cloudfront.net/dev/5970/2020/4/16/d3637faa-3ed6-4b94-8618-858b3057772f/thumbnail.jpg",
                    mention = ALL_MENTION,
                    description = "All members"
                )
            ) + fakeMentions().map { user ->
                MentionModel(
                    id = user.id,
                    pictureUrl = user.pictureUrl,
                    mention = user.userName,
                    description = user.name
                )
            }
        )
    }


    override fun filterMentions(mentions: List<MentionModel>, query: String): List<MentionModel> {
        return mentions.filter {
            it.mention.contains(query, true) || it.description.contains(query, true)
        }
    }

    private suspend fun fakeMentions(): List<UserDto> {
        return listOf(
            UserDto(
                id = 1,
                name = "Alexey Antonov",
                pictureUrl = "https://d3gopequ36f0ms.cloudfront.net/dev/6024/2020/5/18/83b26516-3b80-486e-ba93-c283671e2d07/thumbnail.jpg",
                userName = "al_ant"
            ),
            UserDto(
                id = 2,
                name = "Vladimir Parfenov",
                pictureUrl = "https://s3.amazonaws.com/hellomobil_ee/common_images/default-avatar-thumbnail.jpg",
                userName = "vla_par"
            ),
            UserDto(
                id = 3,
                name = "Andrei Prokofjev ",
                pictureUrl = "https://d3gopequ36f0ms.cloudfront.net/dev/5949/2020/6/4/3a453178-6476-4508-b666-f6b469f4deff/thumbnail.jpg",
                userName = "an_pro"
            ),
            UserDto(
                id = 4,
                name = "Oxana Nebis",
                pictureUrl = "https://d3gopequ36f0ms.cloudfront.net/dev/5970/2020/4/16/d3637faa-3ed6-4b94-8618-858b3057772f/thumbnail.jpg",
                userName = "ox_ne"
            )
        )
    }

    companion object {
        const val ALL_MENTION_ID = -1L
        private const val ALL_MENTION = "all"
    }
}