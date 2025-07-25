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

package net.hyakuninanki.reader.infrastructure.storage

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPreferencesStorage
    @Inject
    constructor(
        @ApplicationContext appContext: Context,
    ) : Storage {
        private val sharedPreferences =
            appContext.getSharedPreferences(KEY_PREFERENCES, Context.MODE_PRIVATE)

        override fun setInt(
            key: String,
            value: Int,
        ) {
            with(sharedPreferences.edit()) {
                putInt(key, value)
                apply()
            }
        }

        override fun getInt(key: String): Int = sharedPreferences.getInt(key, 0)

        companion object {
            private const val KEY_PREFERENCES = "dse2QEKH9xdyNee"
        }
    }
