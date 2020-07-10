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
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import net.hyakuninanki.reader.domain.karuta.model.KarutaRepository
import net.hyakuninanki.reader.domain.question.model.ExamRepository
import net.hyakuninanki.reader.domain.question.model.QuestionRepository
import net.hyakuninanki.reader.infrastructure.database.AppDatabase
import net.hyakuninanki.reader.infrastructure.database.karuta.KarutaRepositoryImpl
import net.hyakuninanki.reader.infrastructure.database.question.ExamRepositoryImpl
import net.hyakuninanki.reader.infrastructure.database.question.QuestionRepositoryImpl
import net.hyakuninanki.reader.infrastructure.storage.Storage
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object DatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            AppDatabase.DB_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideKarutaRepository(
        @ApplicationContext context: Context,
        storage: Storage,
        appDatabase: AppDatabase
    ): KarutaRepository {
        return KarutaRepositoryImpl(context, storage, appDatabase)
    }

    @Provides
    @Singleton
    fun provideExamRepository(appDatabase: AppDatabase): ExamRepository {
        return ExamRepositoryImpl(appDatabase)
    }

    @Provides
    @Singleton
    fun provideQuestionRepository(appDatabase: AppDatabase): QuestionRepository {
        return QuestionRepositoryImpl(appDatabase)
    }
}
