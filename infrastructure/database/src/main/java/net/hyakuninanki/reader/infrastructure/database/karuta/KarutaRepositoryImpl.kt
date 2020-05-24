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

package net.hyakuninanki.reader.infrastructure.database.karuta

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.hyakuninanki.reader.domain.karuta.model.KarutaRepository
import net.hyakuninanki.reader.infrastructure.database.AppDatabase
import java.io.BufferedReader
import java.io.InputStreamReader
import kotlin.coroutines.CoroutineContext

class KarutaRepositoryImpl(
    private val context: Context,
    private val preferences: SharedPreferences,
    private val database: AppDatabase,
    private val ioContext: CoroutineContext = Dispatchers.IO
) : KarutaRepository {
    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun initialize() {
        withContext(ioContext) {
            val karutaJsonVersion = preferences.getInt(
                KarutaJsonConstant.KEY_KARUTA_JSON_VERSION,
                0
            )

            if (karutaJsonVersion < KarutaJsonConstant.KARUTA_VERSION) {
                val inputStream = context.assets.open("karuta_list_v_3.json")
                val reader = BufferedReader(InputStreamReader(inputStream))
                val stringBuilder = StringBuilder()
                var line: String?
                while (true) {
                    line = reader.readLine()
                    if (line == null) {
                        break
                    }
                    stringBuilder.append(line)
                }
                reader.close()

                val karutaDataList = KarutaJsonAdaptor.convert(stringBuilder.toString())

                database.karutaDao().insertKarutas(karutaDataList)

                preferences.edit()
                    .putInt(
                        KarutaJsonConstant.KEY_KARUTA_JSON_VERSION,
                        KarutaJsonConstant.KARUTA_VERSION
                    )
                    .apply()
            }
        }
    }
}
