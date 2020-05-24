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

package net.hyakuninanki.reader.viewstate.core.di

import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers
import net.hyakuninanki.reader.viewstate.core.ActionDispatcher
import net.hyakuninanki.reader.viewstate.core.Dispatcher
import javax.inject.Singleton

@Module
class ViewStateCoreModule {
    @Provides
    @Singleton
    fun provideActionDispatcher(): Dispatcher = ActionDispatcher(AndroidSchedulers.mainThread())
}
