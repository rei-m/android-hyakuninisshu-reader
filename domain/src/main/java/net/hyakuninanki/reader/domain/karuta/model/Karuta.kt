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

import net.hyakuninanki.reader.domain.AbstractEntity

/**
 * 百人一首の歌エンティティ.
 *
 * @param id ID
 * @param no 歌番号
 * @param creator 作者
 * @param kamiNoKu 上の句
 * @param shimoNoKu 下の句
 * @param kimariji 決まり字
 * @param imageNo 画像番号
 * @param translation 訳
 * @param color 百人一首の色
 * @param toriFuda 取り札
 */
class Karuta(
    id: KarutaId,
    val no: KarutaNo,
    val creator: String,
    val kamiNoKu: KamiNoKu,
    val shimoNoKu: ShimoNoKu,
    val kimariji: Kimariji,
    val imageNo: KarutaImageNo,
    val translation: String,
    val color: KarutaColor,
    val toriFuda: ToriFuda
) : AbstractEntity<KarutaId>(id) {
    override fun toString(): String = """
        Karuta(
            no='$no',
            creator='$creator',
            kamiNoKu='$kamiNoKu',
            shimoNoKu='$shimoNoKu',
            kimariji='$kimariji',
            imageNo='$imageNo',
            translation='$translation',
            color='$color',
            toriFuda='$toriFuda'
        )
    """
}
