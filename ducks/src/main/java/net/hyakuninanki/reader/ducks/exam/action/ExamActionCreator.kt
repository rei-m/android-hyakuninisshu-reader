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

package net.hyakuninanki.reader.ducks.exam.action

import android.content.Context
import net.hyakuninanki.reader.domain.question.model.KarutaExamRepository
import net.hyakuninanki.reader.ducks.R
import net.hyakuninanki.reader.ducks.exam.model.KarutaExamResult
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExamActionCreator @Inject constructor(
    private val context: Context,
    private val karutaExamRepository: KarutaExamRepository
) {
    /**
     * 最新の力試しを取得する.
     *
     * @return FetchRecentExamAction
     */
    suspend fun fetchRecent(): FetchRecentExamAction {
        try {
            val recentExam =
                karutaExamRepository.last() ?: return FetchRecentExamAction.createSuccess(null)
            val averageAnswerTimeString = String.format(
                Locale.JAPAN,
                "%.2f",
                recentExam.result.resultSummary.averageAnswerSec
            )
            val karutaExamResult = KarutaExamResult(
                score = recentExam.result.resultSummary.score,
                averageAnswerSecText = context.getString(R.string.seconds, averageAnswerTimeString)
            )
            return FetchRecentExamAction.createSuccess(karutaExamResult)

        } catch (e: Exception) {
            return FetchRecentExamAction.createError(e)
        }
    }
}
