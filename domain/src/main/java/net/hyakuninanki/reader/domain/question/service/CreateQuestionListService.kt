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

package net.hyakuninanki.reader.domain.question.service

import net.hyakuninanki.reader.domain.karuta.model.KarutaNo
import net.hyakuninanki.reader.domain.karuta.model.KarutaNoCollection
import net.hyakuninanki.reader.domain.question.model.Question
import net.hyakuninanki.reader.domain.question.model.QuestionId
import net.hyakuninanki.reader.domain.util.generateRandomIndexArray

class CreateQuestionListService {
    operator fun invoke(
        allKarutaNoCollection: KarutaNoCollection,
        targetKarutaNoCollection: KarutaNoCollection,
        choiceSize: Int
    ): List<Question> {
        if (allKarutaNoCollection.size != KarutaNo.MAX.value) {
            throw IllegalArgumentException("allKarutaList is invalid");
        }

        if (targetKarutaNoCollection.size == 0) {
            throw IllegalArgumentException("targetKarutaNoList is empty");
        }

        return targetKarutaNoCollection.asRandomized.mapIndexed { index, targetKarutaNo ->

            val dupNos =
                allKarutaNoCollection.values.toMutableList().apply { remove(targetKarutaNo) }

            val choices = generateRandomIndexArray(
                dupNos.size,
                choiceSize - 1
            ).map { choiceIndex ->
                dupNos[choiceIndex]
            }.toMutableList()

            val correctPosition = generateRandomIndexArray(choiceSize, 1)[0]
            choices.add(correctPosition, targetKarutaNo)

            Question.createReady(QuestionId(), index + 1, choices, targetKarutaNo)
        };
    }
}