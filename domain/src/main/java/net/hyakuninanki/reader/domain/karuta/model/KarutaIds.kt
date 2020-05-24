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

package net.hyakuninanki.reader.domain.karuta.model

import net.hyakuninanki.reader.domain.util.generateRandomIndexArray

/**
 * 歌IDのコレクション.
 */
data class KarutaIds(val values: List<KarutaId>) {

    /**
     * @return 保持している歌IDの数
     */
    val size: Int = values.size

    /**
     * @return ランダムにソートされた歌IDのリスト
     */
    val asRandomized: List<KarutaId>
        get() = generateRandomIndexArray(values.size, values.size).map { values[it] }

    /**
     * 指定の歌IDを含んでいるか確認する.
     *
     * @param karutaId 確認対象の歌ID
     * @return `true` 含んでいる場合, `false` 含んでいない場合
     */
    operator fun contains(karutaId: KarutaId): Boolean = values.contains(karutaId)
}