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

package net.hyakuninanki.reader.infrastructure.database

import androidx.room.Database
import androidx.room.RoomDatabase
import net.hyakuninanki.reader.infrastructure.database.karuta.KarutaDao
import net.hyakuninanki.reader.infrastructure.database.karuta.KarutaData

@Database(entities = [KarutaData::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun karutaDao(): KarutaDao

    companion object {
        const val DB_NAME = "hyakuninanki_reader_database"
    }
}