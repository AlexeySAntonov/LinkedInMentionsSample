package com.example.mentions

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mentions.adapter.MentionsAdapter
import com.linkedin.android.spyglass.suggestions.interfaces.SuggestionsVisibilityManager
import com.linkedin.android.spyglass.tokenization.QueryToken
import com.linkedin.android.spyglass.tokenization.impl.WordTokenizer
import com.linkedin.android.spyglass.tokenization.impl.WordTokenizerConfig
import com.linkedin.android.spyglass.tokenization.interfaces.QueryTokenReceiver
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), QueryTokenReceiver, SuggestionsVisibilityManager {

  private val viewModel by viewModels<MentionsViewModel>()

  private val mentionsAdapter by lazy {
    MentionsAdapter {
      messageEditText.insertMention(it)
      messageEditText.text.append(' ')
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    initMentions()
    viewModel.mentions.observe(this, Observer { showMentions(it) })
  }

  override fun onResume() {
    super.onResume()
    send.isEnabled = messageEditText.text.isNotBlank()
  }

  override fun onQueryReceived(queryToken: QueryToken): MutableList<String> {
    if (messageEditText.text.endsWith(queryToken.keywords)) {
      viewModel.onMentionChanged(queryToken.keywords)
    }
    return mutableListOf("bucket")
  }

  override fun displaySuggestions(display: Boolean) {
    if (display.not()) viewModel.clearMentions()
  }

  override fun isDisplayingSuggestions(): Boolean = mentions.isVisible

  private fun initMentions() {
    messageEditText.apply {
      tokenizer = WordTokenizer(
        WordTokenizerConfig.Builder()
          .setThreshold(Integer.MAX_VALUE)
          .build()
      )
      setQueryTokenReceiver(this@MainActivity)
      setSuggestionsVisibilityManager(this@MainActivity)
      doAfterTextChanged { send.isEnabled = it?.isNotBlank() == true }
      send.setOnClickListener {
        inputResult.text = messageEditText.text
        messageEditText.text.clear()
      }
    }
    mentions.apply {
      layoutManager = LinearLayoutManager(this@MainActivity)
      adapter = mentionsAdapter
    }
  }

  private fun showMentions(items: List<MentionModel>) {
    mentions.isVisible = items.isNotEmpty()
    mentionsAdapter.items = items
  }
}
