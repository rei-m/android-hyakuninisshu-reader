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

import net.hyakuninanki.reader.domain.ValueObject

/**
 * 下の句.
 */
data class ShimoNoKu(
    val karutaNo: KarutaNo,
    val shiku: Verse,
    val goku: Verse
): ValueObject {
    val kanji: String = "${shiku.kanji}　${goku.kanji}"

    val kana: String = "${shiku.kana}　${goku.kana}"

    override fun toString() = "ShimoNoKu(karutaNo='$karutaNo', kanji='$kanji', kana='$kana')"
}
