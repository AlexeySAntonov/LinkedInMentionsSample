package com.example.mentions

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mentions.business.UserMentionsInteractor
import com.example.mentions.business.UserMentionsInteractorImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MentionsViewModel : ViewModel() {

  private val mentionsInteractor: UserMentionsInteractor = UserMentionsInteractorImpl()

  private val _data = MutableLiveData<List<MentionModel>>()
  val mentions = MutableLiveData<List<MentionModel>>(emptyList())

  init {
    viewModelScope.launch(Dispatchers.IO) {
      mentionsInteractor.data().collect { _data.postValue(it) }
    }
  }

  fun onMentionChanged(query: String) {
    filterMentions(query)
  }

  fun clearMentions() {
    mentions.postValue(emptyList())
  }

  private fun filterMentions(query: String) {
    mentions.postValue(mentionsInteractor.filterMentions(_data.value!!, query))
  }
}