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

package net.hyakuninanki.reader.di

import androidx.savedstate.SavedStateRegistryOwner
import dagger.BindsInstance
import dagger.Subcomponent
import net.hyakuninanki.reader.ui.MainActivity
import net.hyakuninanki.reader.feature.corecomponent.di.ActivityScope
import net.hyakuninanki.reader.feature.examhistory.di.ExamHistoryComponent
import net.hyakuninanki.reader.feature.exammenu.di.ExamMenuComponent
import net.hyakuninanki.reader.feature.examresult.di.ExamResultComponent
import net.hyakuninanki.reader.feature.examstarter.di.ExamStarterComponent
import net.hyakuninanki.reader.feature.material.di.MaterialComponent
import net.hyakuninanki.reader.feature.question.di.QuestionComponent
import net.hyakuninanki.reader.feature.splash.di.SplashComponent
import net.hyakuninanki.reader.feature.trainingresult.di.TrainingResultComponent
import net.hyakuninanki.reader.feature.trainingstarter.di.TrainingStarterComponent

@ActivityScope
@Subcomponent
interface MainComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance owner: SavedStateRegistryOwner): MainComponent
    }

    fun inject(activity: MainActivity)

    fun splashComponent(): SplashComponent.Factory

    fun materialListComponent(): MaterialComponent.Factory

    fun examMenuComponent(): ExamMenuComponent.Factory

    fun trainingStarterComponent(): TrainingStarterComponent.Factory

    fun trainingResultComponent(): TrainingResultComponent.Factory

    fun questionComponent(): QuestionComponent.Factory

    fun examStarterComponent(): ExamStarterComponent.Factory

    fun examResultComponent(): ExamResultComponent.Factory

    fun examHistoryComponent(): ExamHistoryComponent.Factory

    interface Injector : SplashComponent.Injector,
        ExamMenuComponent.Injector,
        MaterialComponent.Injector,
        TrainingStarterComponent.Injector,
        TrainingResultComponent.Injector,
        ExamStarterComponent.Injector,
        ExamResultComponent.Injector,
        ExamHistoryComponent.Injector,
        QuestionComponent.Injector {

        fun mainComponent(): MainComponent

        override fun splashComponent() = mainComponent().splashComponent()
        override fun materialComponent() = mainComponent().materialListComponent()
        override fun examMenuComponent() = mainComponent().examMenuComponent()
        override fun trainingStarterComponent() = mainComponent().trainingStarterComponent()
        override fun trainingResultComponent() = mainComponent().trainingResultComponent()
        override fun questionComponent() = mainComponent().questionComponent()
        override fun examStarterComponent() = mainComponent().examStarterComponent()
        override fun examResultComponent() = mainComponent().examResultComponent()
        override fun examHistoryComponent() = mainComponent().examHistoryComponent()
    }
}
