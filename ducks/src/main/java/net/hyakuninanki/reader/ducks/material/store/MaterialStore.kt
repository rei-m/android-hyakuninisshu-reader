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

package net.hyakuninanki.reader.ducks.material.store

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import net.hyakuninanki.reader.ducks.core.Dispatcher
import net.hyakuninanki.reader.ducks.core.Store
import net.hyakuninanki.reader.ducks.material.action.FetchMaterialListAction
import net.hyakuninanki.reader.ducks.material.model.Material

class MaterialStore(dispatcher: Dispatcher) : Store() {

    private val _materialList = MutableLiveData<List<Material>>(listOf())
    val materialList: LiveData<List<Material>> = _materialList

    init {
        register(dispatcher.on(FetchMaterialListAction::class.java).subscribe {
            if (it.isSucceeded) {
                _materialList.value = it.materialList
            }
        })
    }
}
