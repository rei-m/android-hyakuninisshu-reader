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

package net.hyakuninanki.reader.feature.examhistory.di

import dagger.Subcomponent
import net.hyakuninanki.reader.feature.examhistory.ui.ExamHistoryFragment

@Subcomponent
interface ExamHistoryComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): ExamHistoryComponent
    }

    fun inject(fragment: ExamHistoryFragment)

    interface Injector {
        fun examHistoryComponent(): Factory
    }
}
