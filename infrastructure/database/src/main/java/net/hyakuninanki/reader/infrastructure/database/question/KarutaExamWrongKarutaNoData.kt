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

import androidx.room.*
import net.hyakuninanki.reader.infrastructure.database.karuta.KarutaData

@Entity(
    tableName = "karuta_exam_wrong_karuta_no_table",
    indices = [Index("karuta_exam_id"), Index(("karuta_no"))],
    foreignKeys = [ForeignKey(
        entity = KarutaExamData::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("karuta_exam_id"),
        onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        entity = KarutaData::class,
        parentColumns = arrayOf("no"),
        childColumns = arrayOf("karuta_no"),
        onDelete = ForeignKey.NO_ACTION
    )]
)
data class KarutaExamWrongKarutaNoData(
    @PrimaryKey val id: Long?,
    @ColumnInfo(name = "karuta_exam_id") val karutaExamId: Long,
    @ColumnInfo(name = "karuta_no") val karutaNo: Int
)
