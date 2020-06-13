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

package net.hyakuninanki.reader.state.material.action

import android.content.Context
import net.hyakuninanki.reader.domain.karuta.model.KarutaColor
import net.hyakuninanki.reader.domain.karuta.model.KarutaNo
import net.hyakuninanki.reader.domain.karuta.model.KarutaRepository
import net.hyakuninanki.reader.domain.karuta.model.Kimariji
import net.hyakuninanki.reader.state.core.ext.toMaterial
import net.hyakuninanki.reader.state.material.model.ColorFilter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MaterialActionCreator @Inject constructor(
    private val context: Context,
    private val karutaRepository: KarutaRepository
) {
    /**
     * 指定した色の該当する歌をすべて取り出す.
     *
     *  @param color 五色絞り込み条件
     *
     *  @return FetchMaterialAction
     */
    suspend fun fetchMaterialList(color: ColorFilter?) = try {
        val colors =
            if (color?.value == null) KarutaColor.values().toList() else listOf(color.value)
        val karutaList = karutaRepository.findAllWithCondition(
            fromNo = KarutaNo.MIN,
            toNo = KarutaNo.MAX,
            kimarijis = Kimariji.values().toList(),
            colors = colors
        )

        FetchMaterialListAction.Success(karutaList.map { it.toMaterial(context) })
    } catch (e: Exception) {
        FetchMaterialListAction.Failure(e)
    }
}
