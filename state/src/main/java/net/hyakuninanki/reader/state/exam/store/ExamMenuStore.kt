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

package net.hyakuninanki.reader.state.exam.store

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import net.hyakuninanki.reader.state.core.Dispatcher
import net.hyakuninanki.reader.state.core.Store
import net.hyakuninanki.reader.state.exam.action.FetchRecentExamResultAction
import net.hyakuninanki.reader.state.exam.action.FinishExamAction
import net.hyakuninanki.reader.state.exam.model.ExamResult
import javax.inject.Inject

class ExamMenuStore @Inject constructor(dispatcher: Dispatcher) : Store() {
    private val _recentResult = MutableLiveData<ExamResult>()
    val recentResult: LiveData<ExamResult?> = _recentResult

    init {
        register(dispatcher.on(FetchRecentExamResultAction::class.java).subscribe {
            when (it) {
                is FetchRecentExamResultAction.Success -> {
                    _recentResult.value = it.examResult
                }
                is FetchRecentExamResultAction.Failure -> {
                    // TODO
                }
            }
        }, dispatcher.on(FinishExamAction::class.java).subscribe {
            when (it) {
                is FinishExamAction.Success -> {
                    _recentResult.value = it.examResult
                }
            }
        })
    }
}
