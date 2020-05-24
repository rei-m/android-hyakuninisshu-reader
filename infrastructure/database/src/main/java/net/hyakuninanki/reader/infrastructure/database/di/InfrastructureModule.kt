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

package net.hyakuninanki.reader.infrastructure.database.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import dagger.Module
import dagger.Provides
import net.hyakuninanki.reader.domain.karuta.model.KarutaRepository
import net.hyakuninanki.reader.infrastructure.database.AppDatabase
import net.hyakuninanki.reader.infrastructure.database.karuta.KarutaRepositoryImpl
import javax.inject.Singleton

@Module
class InfrastructureModule {
    @Provides
    @Singleton
    fun provideSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(KEY_PREFERENCES, Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideAppDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java, AppDatabase.DB_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideKarutaRepository(
        context: Context,
        preferences: SharedPreferences,
        appDatabase: AppDatabase
    ): KarutaRepository {
        return KarutaRepositoryImpl(context, preferences, appDatabase)
    }

    companion object {
        private const val KEY_PREFERENCES = "dse2QEKH9xdyNee"
    }
}
