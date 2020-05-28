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

package net.hyakuninanki.reader.viewstate.material.action

import android.content.Context
import net.hyakuninanki.reader.domain.karuta.model.KarutaRepository
import net.hyakuninanki.reader.viewstate.core.ext.toKarutaNoStr
import net.hyakuninanki.reader.viewstate.material.model.ColorFilter
import net.hyakuninanki.reader.viewstate.material.model.Material
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
     *  @return FetchMaterialAction
     */
    suspend fun fetchMaterialList(color: ColorFilter?) = try {
        val karutaList = karutaRepository.findAll(color = color?.value)
        FetchMaterialListAction.createSuccess(karutaList.map { karuta ->
            Material(
                no = karuta.no.value,
                noTxt = karuta.no.value.toKarutaNoStr(context),
                creator = karuta.creator,
                kamiNoKuKana = karuta.kamiNoKu.kana,
                kamiNoKuKanji = karuta.kamiNoKu.kanji,
                shimoNoKuKana = karuta.shimoNoKu.kana,
                shimoNoKuKanji = karuta.shimoNoKu.kanji,
                kimariji = karuta.kimariji.value,
                imageNo = karuta.imageNo.value,
                translation = karuta.translation,
                color = karuta.color.value
            )
        })
    } catch (e: Exception) {
        FetchMaterialListAction.createError(e)
    }
}
