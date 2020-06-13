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

package net.hyakuninanki.reader.state.question.action

import android.content.Context
import net.hyakuninanki.reader.domain.karuta.model.KarutaNo
import net.hyakuninanki.reader.domain.karuta.model.KarutaRepository
import net.hyakuninanki.reader.domain.question.model.QuestionId
import net.hyakuninanki.reader.domain.question.model.QuestionRepository
import net.hyakuninanki.reader.state.core.ext.rawResId
import net.hyakuninanki.reader.state.core.ext.toMaterial
import net.hyakuninanki.reader.state.question.model.Question
import net.hyakuninanki.reader.state.question.model.QuestionState
import net.hyakuninanki.reader.state.question.model.ToriFuda
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.NoSuchElementException
import net.hyakuninanki.reader.domain.question.model.Question as DomainQuestion

@Singleton
class QuestionActionCreator @Inject constructor(
    private val context: Context,
    private val karutaRepository: KarutaRepository,
    private val questionRepository: QuestionRepository
) {
    /**
     * 問題を開始する.
     *
     * @param questionId 問題ID.
     *
     * @return StartQuestionAction
     */
    suspend fun start(
        questionId: String
    ): StartQuestionAction {
        val question = questionRepository.findById(QuestionId(questionId))
            ?: return StartQuestionAction.Failure(NoSuchElementException())
        val choiceKarutaList = karutaRepository.findAllWithNo(question.choiceList)
        val toriFudaList = question.choiceList.map {
            val karuta = choiceKarutaList.find { k -> k.no == it }!!
            ToriFuda(
                karutaNo = karuta.no.value,
                firstLine = karuta.toriFuda.firstLine,
                secondLine = karuta.toriFuda.secondLine,
                thirdLine = karuta.toriFuda.thirdLine
            )
        }
        val state = when (val questionState = question.state) {
            is DomainQuestion.State.Ready,
            is DomainQuestion.State.InAnswer ->
                QuestionState.Ready
            is DomainQuestion.State.Answered ->
                QuestionState.Answered(
                    selectedToriFudaIndex = question.choiceList.indexOf(questionState.result.selectedKarutaNo),
                    isCorrect = questionState.result.judgement.isCorrect,
                    correctMaterial = choiceKarutaList.find { it.no == question.correctNo }!!
                        .toMaterial(context),
                    nextQuestionId = questionRepository.findIdByNo(question.no)?.value
                )
        }
        val count = questionRepository.count()

        return StartQuestionAction.Success(
            Question(
                id = questionId,
                no = question.no,
                position = "${question.no} / $count",
                toriFudaList = toriFudaList,
                yomiFudaKarutaNo = question.correctNo.value
            ),
            state = state
        )
    }

    /**
     * 回答を開始する.
     *
     * @param questionId 問題ID
     * @param startTime 開始時間
     *
     * @return StartQuestionAction
     *
     * @throws NoSuchElementException 問題が見つからなかった場合
     */
    suspend fun startAnswer(questionId: String, startTime: Date): StartAnswerQuestionAction {
        val question = questionRepository.findById(QuestionId(questionId))
            ?: return StartAnswerQuestionAction.Failure(NoSuchElementException())

        questionRepository.save(question.start(startTime))

        val correctKaruta = karutaRepository.findByNo(question.correctNo)
        return StartAnswerQuestionAction.Success(
            state = QuestionState.InAnswer(
                correctKaruta.rawResId(
                    context
                )
            )
        )
    }

    /**
     * 回答する.
     *
     * @param questionId 問題ID
     * @param toriFuda 選択した取札
     *
     * @return AnswerQuestionAction
     *
     * @throws NoSuchElementException 問題が見つからなかった場合
     */
    suspend fun answer(
        questionId: String,
        toriFuda: ToriFuda,
        answerDate: Date
    ): AnswerQuestionAction {
        val question = questionRepository.findById(QuestionId(questionId))
            ?: return AnswerQuestionAction.Failure(NoSuchElementException())

        val selectedKarutaNo = KarutaNo(toriFuda.karutaNo)
        val verified = question.verify(selectedKarutaNo, answerDate)
        questionRepository.save(verified)

        val correctKaruta = karutaRepository.findByNo(verified.correctNo)

        val isCorrect = verified.state.let {
            it is DomainQuestion.State.Answered && it.result.judgement.isCorrect
        }

        return AnswerQuestionAction.Success(
            QuestionState.Answered(
                selectedToriFudaIndex = verified.choiceList.indexOf(selectedKarutaNo),
                isCorrect = isCorrect,
                correctMaterial = correctKaruta.toMaterial(context),
                nextQuestionId = questionRepository.findIdByNo(verified.no + 1)?.value
            )
        )
    }
}
