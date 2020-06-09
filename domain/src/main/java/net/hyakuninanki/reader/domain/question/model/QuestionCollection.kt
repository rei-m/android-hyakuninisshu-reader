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

import net.hyakuninanki.reader.domain.karuta.model.KarutaNoCollection
import java.util.concurrent.TimeUnit

data class QuestionCollection(val values: List<Question>) {
    val isEmpty = values.isEmpty()

    val wrongKarutaNoCollection: KarutaNoCollection by lazy {
        KarutaNoCollection(
            values
                .asSequence()
                .mapNotNull { it.result }
                .filterNot { it.judgement.isCorrect }
                .map { it.judgement.karutaNo }
                .toList()
        )
    }

    val resultSummary: QuestionResultSummary by lazy {
        val quizCount = values.size
        if (quizCount == 0) {
            QuestionResultSummary(0, 0, 0f)
        } else {
            var totalAnswerTimeMillSec: Long = 0

            var collectCount = 0

            values.forEach {
                val result = it.result ?: throw IllegalStateException("Training is not finished.")

                totalAnswerTimeMillSec += result.answerMillSec
                if (result.judgement.isCorrect) {
                    collectCount++
                }
            }

            val averageAnswerTime =
                totalAnswerTimeMillSec.toFloat() / quizCount.toFloat() / TimeUnit.SECONDS.toMillis(1)
                    .toFloat()

            QuestionResultSummary(quizCount, collectCount, averageAnswerTime)
        }
    }
}
