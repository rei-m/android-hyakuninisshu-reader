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

package net.hyakuninanki.reader.infrastructure.database.question

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import net.hyakuninanki.reader.domain.karuta.model.KarutaNo
import net.hyakuninanki.reader.domain.karuta.model.KarutaNoCollection
import net.hyakuninanki.reader.domain.question.model.*
import net.hyakuninanki.reader.infrastructure.database.AppDatabase
import java.util.*
import kotlin.coroutines.CoroutineContext

class KarutaExamRepositoryImpl(
    private val database: AppDatabase,
    private val ioContext: CoroutineContext = Dispatchers.IO
) : KarutaExamRepository {
    override suspend fun add(karutaExamResult: KarutaExamResult, tookExamDate: Date): KarutaExamId =
        withContext(ioContext) {
            return@withContext database.runInTransaction<Deferred<KarutaExamId>> {
                return@runInTransaction async {
                    val karutaExamData = KarutaExamData(
                        id = null,
                        tookExamDate = tookExamDate,
                        totalQuestionCount = karutaExamResult.resultSummary.totalQuestionCount,
                        averageAnswerTime = karutaExamResult.resultSummary.averageAnswerSec
                    )
                    val karutaExamDataId = database.karutaExamDao().insert(karutaExamData)
                    database.karutaExamWrongKarutaNoDao()
                        .insert(karutaExamResult.wrongKarutaNoCollection.values.map {
                            KarutaExamWrongKarutaNoData(
                                id = null,
                                karutaExamId = karutaExamDataId,
                                karutaNo = it.value
                            )
                        })
                    return@async KarutaExamId(
                        karutaExamDataId
                    )
                }
            }
        }.await()

    override suspend fun deleteList(list: List<KarutaExam>) {
        withContext(ioContext) {
            list.map {
                KarutaExamData(
                    id = it.id.value,
                    tookExamDate = it.tookDate,
                    totalQuestionCount = it.result.resultSummary.totalQuestionCount,
                    averageAnswerTime = it.result.resultSummary.averageAnswerSec
                )
            }.let {
                database.karutaExamDao().deleteExams(it)
            }
        }
    }

    override suspend fun findById(karutaExamId: KarutaExamId): KarutaExam? {
        return withContext(ioContext) {
            val karutaExamData =
                database.karutaExamDao().findById(karutaExamId.value) ?: return@withContext null
            return@withContext dataToModel(karutaExamData)
        }
    }

    override suspend fun last(): KarutaExam? {
        return withContext(ioContext) {
            val karutaExamData = database.karutaExamDao().last() ?: return@withContext null
            return@withContext dataToModel(karutaExamData)
        }
    }

    override suspend fun findCollection(): KarutaExamCollection {
        return withContext(ioContext) {
            val karutaExamDataList = database.karutaExamDao().findAll()
            val wrongKarutaNoDataList = database.karutaExamWrongKarutaNoDao().findAll()
            val wrongKarutaNoDataMap: HashMap<Long, MutableList<KarutaNo>> = hashMapOf()
            wrongKarutaNoDataList.forEach {
                if (wrongKarutaNoDataMap.containsKey(it.karutaExamId)) {
                    wrongKarutaNoDataMap[it.karutaExamId]!!.add(KarutaNo(it.karutaNo))
                } else {
                    wrongKarutaNoDataMap[it.karutaExamId] = mutableListOf(KarutaNo(it.karutaNo))
                }
            }

            return@withContext karutaExamDataList.map { karutaExamData ->
                val id = KarutaExamId(karutaExamData.id!!)
                val wrongKarutaNoCollection = KarutaNoCollection(
                    wrongKarutaNoDataMap[karutaExamData.id] ?: listOf()
                )
                val resultSummary = QuestionResultSummary(
                    totalQuestionCount = karutaExamData.totalQuestionCount,
                    correctCount = karutaExamData.totalQuestionCount - wrongKarutaNoCollection.size,
                    averageAnswerSec = karutaExamData.averageAnswerTime
                )
                return@map KarutaExam(
                    id = id,
                    tookDate = karutaExamData.tookExamDate,
                    result = KarutaExamResult(
                        resultSummary = resultSummary,
                        wrongKarutaNoCollection = wrongKarutaNoCollection
                    )
                )
            }.let {
                KarutaExamCollection(it)
            }
        }
    }

    private fun dataToModel(karutaExamData: KarutaExamData): KarutaExam {
        val wrongKarutaNoDataList = database.karutaExamWrongKarutaNoDao().findAllWithExamId(
            karutaExamId = karutaExamData.id!!
        )
        return KarutaExam(
            id = KarutaExamId(karutaExamData.id),
            tookDate = karutaExamData.tookExamDate,
            result = KarutaExamResult(
                resultSummary = QuestionResultSummary(
                    totalQuestionCount = karutaExamData.totalQuestionCount,
                    correctCount = karutaExamData.totalQuestionCount - wrongKarutaNoDataList.size,
                    averageAnswerSec = karutaExamData.averageAnswerTime
                ),
                wrongKarutaNoCollection = KarutaNoCollection(wrongKarutaNoDataList.map {
                    KarutaNo(
                        it.karutaNo
                    )
                })
            )
        )
    }
}
