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

package net.hyakuninanki.reader.domain.question

import java.util.*

interface KarutaExamRepository {
    /**
     * 力試しを新規に永続化する.
     *
     * @param karutaExamResult 力試しの結果
     * @param tookExamDate 力試しを受けた日付
     * @return 登録した力試しのID
     */
    suspend fun add(karutaExamResult: KarutaExamResult, tookExamDate: Date): KarutaExamId

    /**
     * 力試しを取得する.
     *
     * @param karutaExamId 力試しID
     * @return 力試し
     */
    suspend fun findById(karutaExamId: KarutaExamId): KarutaExam?

    /**
     * 力試しコレクションを取得する.
     *
     * @return 力試しコレクション
     */
    suspend fun findCollection(): KarutaExams
}
