/*
 * Copyright (c) 2020. Rei Matsushita.
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

package net.hyakuninanki.reader.feature.exammenu.ui

import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import net.hyakuninanki.reader.ducks.core.Dispatcher
import net.hyakuninanki.reader.ducks.exam.action.ExamActionCreator
import net.hyakuninanki.reader.ducks.exam.store.ExamMenuStore
import net.hyakuninanki.reader.feature.corecomponent.ui.AbstractViewModel
import javax.inject.Inject

class ExamMenuViewModel(
    dispatcher: Dispatcher,
    private val actionCreator: ExamActionCreator,
    private val store: ExamMenuStore
) : AbstractViewModel(dispatcher) {

    val hasResult = Transformations.map(store.recentResult) { it != null }
    val score = Transformations.map(store.recentResult) { it?.score }
    val averageAnswerSec = Transformations.map(store.recentResult) { it?.averageAnswerSecText }

    init {
        dispatchAction { actionCreator.fetchRecent() }
    }

    override fun onCleared() {
        store.dispose()
        super.onCleared()
    }

    class Factory @Inject constructor(
        private val dispatcher: Dispatcher,
        private val actionCreator: ExamActionCreator,
        private val store: ExamMenuStore
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T = ExamMenuViewModel(
            dispatcher,
            actionCreator,
            store
        ) as T
    }
}
