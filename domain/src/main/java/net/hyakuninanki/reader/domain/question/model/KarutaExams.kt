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

package net.hyakuninanki.reader.domain.question.model

import net.hyakuninanki.reader.domain.karuta.model.KarutaNo
import net.hyakuninanki.reader.domain.karuta.model.KarutaNoCollection

/**
 * 力試しのコレクション.
 */
data class KarutaExams(private val values: List<KarutaExam>) {

    /**
     * すべての力試し.
     */
    val all: List<KarutaExam> = values

    /**
     * 直近の力試し.
     */
    val recent: KarutaExam? = values.firstOrNull()

    /**
     * 過去の力試しの結果から間違えた問題の歌の番号の集合.
     */
    val totalWrongKarutaNoCollection: KarutaNoCollection =
        KarutaNoCollection(
            values.asSequence().fold(mutableSetOf<KarutaNo>()) { karutaNoSet, karutaExam ->
                karutaNoSet.addAll(karutaExam.result.wrongKarutaNoCollection.values)
                karutaNoSet
            }.toList()
        )

    override fun equals(other: Any?): Boolean {
        if (other !is KarutaExams) return false
        return values == other.values
    }

    override fun hashCode() = values.hashCode()

    override fun toString() = "KarutaExams(values=$values)"
}
