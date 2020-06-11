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

package net.hyakuninanki.reader.state.exam.action

import android.content.Context
import net.hyakuninanki.reader.domain.karuta.model.KarutaNo
import net.hyakuninanki.reader.domain.karuta.model.KarutaNoCollection
import net.hyakuninanki.reader.domain.karuta.model.KarutaRepository
import net.hyakuninanki.reader.domain.question.model.*
import net.hyakuninanki.reader.domain.question.service.CreateQuestionListService
import net.hyakuninanki.reader.state.R
import net.hyakuninanki.reader.state.core.ext.diffString
import net.hyakuninanki.reader.state.core.ext.toResult
import net.hyakuninanki.reader.state.core.ext.toText
import net.hyakuninanki.reader.state.exam.model.ExamResult
import net.hyakuninanki.reader.state.material.model.Material
import net.hyakuninanki.reader.state.question.model.QuestionResult
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.NoSuchElementException

@Singleton
class ExamActionCreator @Inject constructor(
    private val context: Context,
    private val karutaRepository: KarutaRepository,
    private val questionRepository: QuestionRepository,
    private val examRepository: ExamRepository,
    private val createQuestionListService: CreateQuestionListService
) {
    /**
     * 力試しを開始する.
     *
     * @return StartExamAction
     */
    suspend fun start() = try {
        val allKarutaNoCollection = KarutaNoCollection(KarutaNo.LIST)
        // TODO: あとでtakeを消す
        val targetKarutaList = karutaRepository.findAll().take(1)

        val targetKarutaNoCollection = KarutaNoCollection(targetKarutaList.map { it.no })

        val questionList = createQuestionListService(
            allKarutaNoCollection,
            targetKarutaNoCollection,
            Question.CHOICE_SIZE
        )

        questionRepository.initialize(questionList)

        StartExamAction.Success(questionList.first().id.value)
    } catch (e: Exception) {
        StartExamAction.Failure(e)
    }

    /**
     * 力試しを終了して結果を登録する.
     *
     * @return FinishExamAction
     */
    suspend fun finish(now: Date = Date()) = try {
        val questionCollection = questionRepository.findCollection()
        val result = ExamResult(
            questionCollection.resultSummary,
            questionCollection.wrongKarutaNoCollection
        )
        val addedExamId = examRepository.add(result, now)
        val examCollection = examRepository.findCollection()
        examRepository.deleteList(examCollection.overflowed)

        val averageAnswerTimeString = String.format(
            Locale.JAPAN,
            "%.2f",
            result.resultSummary.averageAnswerSec
        )

        FinishExamAction.Success(
            examResult = ExamResult(
                id = addedExamId.value,
                score = result.resultSummary.score,
                averageAnswerSecText = context.getString(R.string.seconds, averageAnswerTimeString),
                questionResultList = KarutaNo.LIST.map { karutaNo ->
                    QuestionResult(
                        karutaNo = karutaNo.value,
                        karutaNoText = karutaNo.toText(context),
                        isCorrect = !result.wrongKarutaNoCollection.contains(karutaNo)
                    )
                },
                fromNowText = now.diffString(context, now)
            )
        )
    } catch (e: Exception) {
        FinishExamAction.Failure(e)
    }

    /**
     * 最新の力試しを取得する.
     *
     * @return FetchRecentExamAction
     */
    suspend fun fetchRecentResult(now: Date = Date()): FetchRecentExamResultAction {
        try {
            val recentExam =
                examRepository.last() ?: return FetchRecentExamResultAction.Success(null)
            return FetchRecentExamResultAction.Success(recentExam.toResult(context, now))

        } catch (e: Exception) {
            return FetchRecentExamResultAction.Failure(e)
        }
    }

    suspend fun fetchResult(id: Long, now: Date = Date()): FetchExamResultAction {
        val exam = examRepository.findById(ExamId(id))
            ?: return FetchExamResultAction.Failure(NoSuchElementException())
        return FetchExamResultAction.Success(
            examResult = exam.toResult(context, now),
            materialList = karutaRepository.findAll().map { Material.createFromKaruta(it, context) }
        )
    }

    suspend fun fetchAllResult(now: Date = Date()): FetchAllExamResultAction {
        val examCollection = examRepository.findCollection()
        return FetchAllExamResultAction.Success(
            examResultList = examCollection.all.map { it.toResult(context, now) }
        )
    }
}
