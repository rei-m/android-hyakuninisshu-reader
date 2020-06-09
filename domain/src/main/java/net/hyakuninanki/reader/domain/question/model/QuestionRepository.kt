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

interface QuestionRepository {
    /**
     * 問題を初期化する.トレーニングまたは力試しを開始する毎に初期化する.
     *
     * @param questionList 問題リスト
     */
    suspend fun initialize(questionList: List<Question>)

    suspend fun count(): Int

    suspend fun findById(questionId: QuestionId): Question?

    suspend fun findIdByNo(no: Int): QuestionId?

    suspend fun save(question: Question)
}
