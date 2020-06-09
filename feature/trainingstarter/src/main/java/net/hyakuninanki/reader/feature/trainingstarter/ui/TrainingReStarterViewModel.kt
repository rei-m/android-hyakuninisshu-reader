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

package net.hyakuninanki.reader.feature.trainingstarter.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import net.hyakuninanki.reader.feature.corecomponent.ext.combineLatest
import net.hyakuninanki.reader.feature.corecomponent.ext.map
import net.hyakuninanki.reader.feature.corecomponent.ui.AbstractViewModel
import net.hyakuninanki.reader.state.core.Dispatcher
import net.hyakuninanki.reader.state.training.action.TrainingActionCreator
import net.hyakuninanki.reader.state.training.store.TrainingStore
import javax.inject.Inject

class TrainingReStarterViewModel(
    dispatcher: Dispatcher,
    actionCreator: TrainingActionCreator,
    private val store: TrainingStore
) : AbstractViewModel(dispatcher), TrainingStarterViewModelProperties {

    override val onReadyEvent = store.onReadyEvent

    override val isEmpty = store.isEmpty

    override val isFailure = store.isFailure

    override val isVisibleProgress =
        isEmpty.combineLatest(isFailure).map { (_isEmpty, _isFailure) ->
            return@map !_isEmpty && !_isFailure
        }

    init {
        dispatchAction {
            actionCreator.restart()
        }
    }

    override fun onCleared() {
        store.dispose()
        super.onCleared()
    }

    class Factory @Inject constructor(
        private val dispatcher: Dispatcher,
        private val actionCreator: TrainingActionCreator,
        private val store: TrainingStore
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T = TrainingReStarterViewModel(
            dispatcher,
            actionCreator,
            store
        ) as T
    }
}