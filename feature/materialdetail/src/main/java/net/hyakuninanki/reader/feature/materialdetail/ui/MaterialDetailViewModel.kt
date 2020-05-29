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

package net.hyakuninanki.reader.feature.materialdetail.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import net.hyakuninanki.reader.ducks.core.Dispatcher
import net.hyakuninanki.reader.ducks.material.action.MaterialActionCreator
import net.hyakuninanki.reader.ducks.material.model.ColorFilter
import net.hyakuninanki.reader.ducks.material.store.MaterialStore
import net.hyakuninanki.reader.feature.corecomponent.ui.AbstractViewModel
import javax.inject.Inject

class MaterialDetailViewModel(
    dispatcher: Dispatcher,
    private val actionCreator: MaterialActionCreator,
    private val store: MaterialStore,
    colorFilter: ColorFilter
) : AbstractViewModel(dispatcher) {
    val materialList = store.materialList

    init {
        dispatchAction { actionCreator.fetchMaterialList(colorFilter) }
    }

    override fun onCleared() {
        store.dispose()
        super.onCleared()
    }

    class Factory @Inject constructor(
        private val dispatcher: Dispatcher,
        private val actionCreator: MaterialActionCreator,
        private val store: MaterialStore
    ) : ViewModelProvider.Factory {

        var colorFilter: ColorFilter = ColorFilter.ALL

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>) = MaterialDetailViewModel(
            dispatcher,
            actionCreator,
            store,
            colorFilter
        ) as T
    }
}
