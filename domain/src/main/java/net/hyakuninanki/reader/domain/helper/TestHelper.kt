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

package net.hyakuninanki.reader.domain.helper

import androidx.annotation.VisibleForTesting
import net.hyakuninanki.reader.domain.karuta.model.KarutaNo
import net.hyakuninanki.reader.domain.karuta.model.KarutaNoCollection
import net.hyakuninanki.reader.domain.question.model.*
import java.util.*

@VisibleForTesting
interface TestHelper {
    fun createQuestionReady(
        id: QuestionId = QuestionId(),
        no: Int = 1,
        choiceList: List<KarutaNo> = listOf(
            KarutaNo(1),
            KarutaNo(2),
            KarutaNo(3),
            KarutaNo(4),
            KarutaNo(5),
            KarutaNo(6),
            KarutaNo(7),
            KarutaNo(8),
            KarutaNo(9)
        ),
        correctNo: KarutaNo = KarutaNo(3)
    ): Question = Question(
        id = id,
        no = no,
        choiceList = choiceList,
        correctNo = correctNo,
        state = Question.State.Ready
    )

    fun createQuestionInAnswer(
        id: QuestionId = QuestionId(),
        no: Int = 1,
        choiceList: List<KarutaNo> = listOf(
            KarutaNo(1),
            KarutaNo(2),
            KarutaNo(3),
            KarutaNo(4),
            KarutaNo(5),
            KarutaNo(6),
            KarutaNo(7),
            KarutaNo(8),
            KarutaNo(9)
        ),
        correctNo: KarutaNo = KarutaNo(3),
        startDate: Date = Date()
    ): Question = Question(
        id = id,
        no = no,
        choiceList = choiceList,
        correctNo = correctNo,
        state = Question.State.InAnswer(startDate = startDate)
    )

    fun createQuestionAnswered(
        id: QuestionId = QuestionId(),
        no: Int = 1,
        choiceList: List<KarutaNo> = listOf(
            KarutaNo(1),
            KarutaNo(2),
            KarutaNo(3),
            KarutaNo(4),
            KarutaNo(5),
            KarutaNo(6),
            KarutaNo(7),
            KarutaNo(8),
            KarutaNo(9)
        ),
        correctNo: KarutaNo = KarutaNo(3),
        startDate: Date = Date(),
        selectedKarutaNo: KarutaNo = KarutaNo(3),
        answerMillSec: Long = 9800,
        isCorrect: Boolean = true
    ): Question = Question(
        id = id,
        no = no,
        choiceList = choiceList,
        correctNo = correctNo,
        state = Question.State.Answered(
            startDate = startDate,
            result = QuestionResult(
                selectedKarutaNo = selectedKarutaNo,
                answerMillSec = answerMillSec,
                judgement = QuestionJudgement(
                    karutaNo = correctNo,
                    isCorrect = isCorrect
                )
            )
        )
    )

    fun createExam(
        tookDate: Date = Date(),
        totalQuestionCount: Int = 10,
        correctCount: Int = 9,
        averageAnswerSec: Float = 2.67f,
        wrongKarutaNoCollection: KarutaNoCollection = KarutaNoCollection(listOf(KarutaNo(1)))
    ): Exam = Exam(
        id = ExamId(generateExamId()),
        tookDate = tookDate,
        result = ExamResult(
            resultSummary = QuestionResultSummary(
                totalQuestionCount = totalQuestionCount,
                correctCount = correctCount,
                averageAnswerSec = averageAnswerSec
            ),
            wrongKarutaNoCollection = wrongKarutaNoCollection
        )
    )

    companion object {
        @Volatile
        private var examIdSequence: Long = 1

        private fun generateExamId(): Long {
            synchronized(this) {
                examIdSequence += 1
                return examIdSequence
            }
        }
    }
}