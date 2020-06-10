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

import net.hyakuninanki.reader.feature.corecomponent.ext.combineLatest
import net.hyakuninanki.reader.feature.corecomponent.ext.map
import net.hyakuninanki.reader.feature.corecomponent.ui.AbstractViewModel
import net.hyakuninanki.reader.state.core.Dispatcher
import net.hyakuninanki.reader.state.training.store.TrainingStore

abstract class BaseTrainingStarterVIewModel(
    private val store: TrainingStore,
    dispatcher: Dispatcher
) : AbstractViewModel(dispatcher) {
    val onReadyEvent = store.onReadyEvent

    val isEmpty = store.isEmpty

    val isFailure = store.isFailure

    val isVisibleProgress =
        isEmpty.combineLatest(isFailure).map { (_isEmpty, _isFailure) ->
            return@map !_isEmpty && !_isFailure
        }

    override fun onCleared() {
        store.dispose()
        super.onCleared()
    }
}