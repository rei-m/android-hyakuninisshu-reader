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

package net.hyakuninanki.reader.ducks.training.action

import net.hyakuninanki.reader.domain.karuta.model.*
import net.hyakuninanki.reader.domain.question.model.QuestionRepository
import net.hyakuninanki.reader.domain.question.service.CreateQuestionListService
import net.hyakuninanki.reader.ducks.training.model.ColorCondition
import net.hyakuninanki.reader.ducks.training.model.KimarijiCondition
import net.hyakuninanki.reader.ducks.training.model.RangeFromCondition
import net.hyakuninanki.reader.ducks.training.model.RangeToCondition
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TrainingActionCreator @Inject constructor(
    private val karutaRepository: KarutaRepository,
    private val questionRepository: QuestionRepository
) {
    /**
     * 練習を開始する.
     *
     * @return StartTrainingAction
     */
    suspend fun start(
        fromCondition: RangeFromCondition,
        toCondition: RangeToCondition,
        kimariji: KimarijiCondition,
        color: ColorCondition
    ) = try {
        val allKarutaNoCollection = KarutaNoCollection(KarutaNo.LIST)
        val targetKarutaList = karutaRepository.findAllWithCondition(
            fromNo = fromCondition.value,
            toNo = toCondition.value,
            kimarijis = if (kimariji.value != null) listOf(kimariji.value) else Kimariji.values()
                .toList(),
            colors = if (color.value != null) listOf(color.value) else KarutaColor.values().toList()
        )

        if (targetKarutaList.isEmpty()) {
            StartTrainingAction.createEmpty()
        } else {
            val targetKarutaNoCollection = KarutaNoCollection(targetKarutaList.map { it.no })

            val questionList =
                CreateQuestionListService()(allKarutaNoCollection, targetKarutaNoCollection)

            questionRepository.initialize(questionList)

            StartTrainingAction.createSuccess(questionList.first().id.value)
        }
    } catch (e: Exception) {
        StartTrainingAction.createError(e)
    }
}
