/*
 * Copyright (c) 2025. Rei Matsushita.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package net.hyakuninanki.reader.feature.question.ui

import android.content.Context
import android.media.MediaPlayer
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.map
import dagger.hilt.android.qualifiers.ApplicationContext
import net.hyakuninanki.reader.feature.corecomponent.ui.AbstractViewModel
import net.hyakuninanki.reader.feature.question.R
import net.hyakuninanki.reader.state.core.Dispatcher
import net.hyakuninanki.reader.state.question.action.QuestionActionCreator
import net.hyakuninanki.reader.state.question.model.QuestionState
import net.hyakuninanki.reader.state.question.store.QuestionStore
import net.hyakuninanki.reader.state.training.model.DisplayModeCondition
import net.hyakuninanki.reader.state.training.model.InputSecondCondition
import java.util.*
import javax.inject.Inject

class QuestionViewModel(
    private val questionId: String,
    displayMode: DisplayModeCondition,
    inputSecond: InputSecondCondition,
    canReplay: Boolean,
    dispatcher: Dispatcher,
    private val actionCreator: QuestionActionCreator,
    private val store: QuestionStore,
    context: Context,
) : AbstractViewModel(dispatcher) {
    val question = store.question

    val position = question.map { it.position }

    val toriFudaList =
        question.map {
            it.toriFudaList.map { toriFuda -> Pair(toriFuda, displayMode) }
        }

    val inputSecondCounter: MutableLiveData<Int> = MutableLiveData()

    val state = store.state

    val isVisibleWaitingMask = state.map { it is QuestionState.Ready }

    val isVisibleResult = state.map { it is QuestionState.Answered }

    val selectedTorifudaIndex =
        state.map {
            if (it is QuestionState.Answered) {
                return@map it.selectedToriFudaIndex
            } else {
                return@map null
            }
        }

    val isCorrectResId =
        state.map {
            val resId =
                if (it is QuestionState.Answered &&
                    it.isCorrect
                ) {
                    net.hyakuninanki.reader.feature.corecomponent.R.drawable.check_correct
                } else {
                    net.hyakuninanki.reader.feature.corecomponent.R.drawable.check_incorrect
                }
            ContextCompat.getDrawable(context, resId)
        }

    private val _isVisibleReplayButton = MutableLiveData(false)
    val isVisibleReplayButton: LiveData<Boolean> = _isVisibleReplayButton

    private var mediaPlayer: MediaPlayer? = null

    private val timer = Timer()

    private val timerTask =
        object : TimerTask() {
            private var count = inputSecond.value

            override fun run() {
                if (count == 0) {
                    timer.cancel()
                    dispatchAction {
                        actionCreator.startAnswer(questionId, Date())
                    }
                }
                inputSecondCounter.postValue(count)
                count -= 1
            }
        }

    private val stateObserver =
        Observer<QuestionState> {
            when (it) {
                is QuestionState.Ready -> {
                    timer.scheduleAtFixedRate(timerTask, 0, 1000)
                }
                is QuestionState.InAnswer -> {
                    mediaPlayer =
                        MediaPlayer.create(context, it.rawResId).apply {
                            if (canReplay) {
                                setOnCompletionListener {
                                    _isVisibleReplayButton.postValue(true)
                                }
                            }
                        }
                    mediaPlayer?.start()
                }
                is QuestionState.Answered -> {
                    _isVisibleReplayButton.postValue(false)
                    mediaPlayer?.setOnCompletionListener(null)
                }
            }
        }

    private var isSelected = false

    init {
        state.observeForever(stateObserver)

        dispatchAction {
            actionCreator.start(questionId)
        }
    }

    override fun onCleared() {
        state.removeObserver(stateObserver)
        mediaPlayer?.stop()
        mediaPlayer?.setOnCompletionListener(null)
        mediaPlayer?.release()
        mediaPlayer = null
        timer.cancel()
        store.dispose()
        super.onCleared()
    }

    fun onSelected(position: Int) {
        question.value?.let {
            if (isSelected) {
                return@let
            }
            isSelected = true

            dispatchAction {
                actionCreator.answer(questionId, it.toriFudaList[position], Date())
            }
        }
    }

    fun onClickedMask() {
        return
    }

    fun onClickReplay() {
        mediaPlayer?.let {
            if (it.isPlaying) {
                return@let
            }
            _isVisibleReplayButton.value = false
            it.start()
        }
    }

    class Factory
        @Inject
        constructor(
            private val dispatcher: Dispatcher,
            private val actionCreator: QuestionActionCreator,
            private val store: QuestionStore,
            @param:ApplicationContext private val context: Context,
        ) : ViewModelProvider.Factory {
            lateinit var questionId: String
            lateinit var displayMode: DisplayModeCondition
            lateinit var inputSecond: InputSecondCondition
            var canReplay: Boolean = false

            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T =
                QuestionViewModel(
                    questionId,
                    displayMode,
                    inputSecond,
                    canReplay,
                    dispatcher,
                    actionCreator,
                    store,
                    context,
                ) as T
        }
}
