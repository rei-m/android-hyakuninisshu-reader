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

import net.hyakuninanki.reader.domain.AbstractEntity
import net.hyakuninanki.reader.domain.karuta.model.KarutaNo
import java.util.*

/**
 * 百人一首の問題.
 */
class Question private constructor(
    id: QuestionId,
    val choiceList: List<KarutaNo>,
    val correctNo: KarutaNo,
    startDate: Date? = null,
    result: QuestionResult? = null
) : AbstractEntity<QuestionId>(id) {

    var startDate: Date? = startDate
        private set

    var result: QuestionResult? = result
        private set

    val state: State
        get() = if (startDate == null && result == null) {
            State.READY
        } else if (startDate != null && result == null) {
            State.IN_ANSWER
        } else {
            State.ANSWERED
        }

    /**
     * 解答を開始する.
     *
     * @param startDate 解答開始時間
     * @return 問題
     */
    fun start(startDate: Date): Question {
        this.startDate = startDate
        return this
    }

    /**
     * 選択肢が正解か判定する.
     *
     * @param choiceNo 選択肢番号
     * @param answerDate 解答した時間
     * @return 解答後の問題
     * @throws IllegalStateException 解答開始していない場合
     */
    @Throws(IllegalStateException::class)
    fun verify(choiceNo: ChoiceNo, answerDate: Date): Question {
        val startTime = startDate?.time ?: let {
            throw IllegalStateException("Quiz is not started. Call start.")
        }
        val answerTime = answerDate.time - startTime
        val selectedNo = choiceList[choiceNo.asIndex]
        val judgement =
            QuestionJudgement(
                correctNo,
                correctNo == selectedNo
            )
        this.result =
            QuestionResult(
                choiceNo,
                answerTime,
                judgement
            )
        return this
    }

    override fun toString() =
        "KarutaQuiz(choiceList=$choiceList, correctNo=$correctNo, startDate=$startDate, result=$result)"

    enum class State {
        READY, IN_ANSWER, ANSWERED,
    }

    companion object {
        fun createReady(
            id: QuestionId,
            choiceList: List<KarutaNo>,
            correctNo: KarutaNo
        ) = Question(
            id,
            choiceList,
            correctNo
        )

        fun createInAnswer(
            id: QuestionId,
            choiceList: List<KarutaNo>,
            correctNo: KarutaNo,
            startDate: Date
        ) = Question(
            id,
            choiceList,
            correctNo,
            startDate
        )

        fun createAnswered(
            id: QuestionId,
            choiceList: List<KarutaNo>,
            correctNo: KarutaNo,
            startDate: Date,
            answerMillSec: Long,
            choiceNo: ChoiceNo,
            isCorrect: Boolean
        ): Question {

            val judgement =
                QuestionJudgement(
                    correctNo,
                    isCorrect
                )
            val result =
                QuestionResult(
                    choiceNo,
                    answerMillSec,
                    judgement
                )

            return Question(
                id,
                choiceList,
                correctNo,
                startDate,
                result
            )
        }
    }
}
