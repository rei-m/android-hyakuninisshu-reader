/*
 * Copyright (c) 2025. Rei Matsushita.
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

package net.hyakuninanki.reader.infrastructure.database.karuta

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface KarutaDao {
    @Query(
        "SELECT * from karuta_table " +
            "WHERE " +
            "    `no` = :no",
    )
    fun findByNo(no: Int): KarutaData

    @Query(
        "SELECT * from karuta_table " +
            "WHERE " +
            "    `no` BETWEEN :fromNo AND :toNo " +
            "AND kimariji IN (:kimarijis) " +
            "AND color IN (:colors) " +
            "ORDER BY " +
            "    `no` ASC",
    )
    fun findAllWithCondition(
        fromNo: Int,
        toNo: Int,
        kimarijis: List<Int>,
        colors: List<String>,
    ): List<KarutaData>

    @Query(
        "SELECT * from karuta_table " +
            "WHERE " +
            "    `no` IN (:nos)" +
            "ORDER BY " +
            "    `no` ASC",
    )
    fun findAllWithNo(nos: List<Int>): List<KarutaData>

    @Query(
        "SELECT * from karuta_table " +
            "ORDER BY " +
            "    `no` ASC",
    )
    fun findAll(): List<KarutaData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKarutas(karutas: List<KarutaData>)

    @Query("DELETE FROM karuta_table")
    suspend fun deleteAll()
}
