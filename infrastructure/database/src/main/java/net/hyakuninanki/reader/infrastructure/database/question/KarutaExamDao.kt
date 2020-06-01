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

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface KarutaExamDao {
    @Query("SELECT * from karuta_exam_table ORDER BY `id` DESC LIMIT 1")
    fun last(): KarutaExamData?

//    @Query("SELECT * from karuta_table WHERE color = :color ORDER BY `no` ASC")
//    fun findAllWithColor(color: String): List<KarutaData>

    @Insert
    suspend fun insert(karutaExam: KarutaExamData): Long

//    @Query("DELETE FROM karuta_table")
//    suspend fun deleteAll()
}
