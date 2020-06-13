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

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.reactivex.schedulers.Schedulers
import net.hyakuninanki.reader.state.core.ActionDispatcher
import net.hyakuninanki.reader.state.core.Dispatcher
import net.hyakuninanki.reader.state.exam.action.FetchRecentExamResultAction
import net.hyakuninanki.reader.state.exam.action.FinishExamAction
import net.hyakuninanki.reader.state.exam.model.ExamResult
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ExamMenuStoreTest {
    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var dispatcher: Dispatcher
    private lateinit var store: ExamMenuStore

    private val value = ExamResult(
        id = 1,
        score = "score",
        averageAnswerSecText = "averageAnswerSecText",
        questionResultList = listOf(),
        fromNowText = "fromNowText"
    )

    @Before
    fun setUp() {
        dispatcher = ActionDispatcher(Schedulers.trampoline())
        store = ExamMenuStore(dispatcher)
    }

    @After
    fun tearDown() {
        store.dispose()
    }

    @Test
    fun initialState() {
        assertThat(store.recentResult.value).isNull()
        assertThat(store.isFailure.value).isFalse()
    }

    @Test
    fun state_receivedFetchRecentExamResultAction() {
        dispatcher.dispatch(FetchRecentExamResultAction.Success(value))
        assertThat(store.recentResult.value).isEqualTo(value)
        assertThat(store.isFailure.value).isFalse()
    }

    @Test
    fun state_receivedFailedFetchRecentExamResultAction() {
        dispatcher.dispatch(FetchRecentExamResultAction.Failure(RuntimeException()))
        assertThat(store.recentResult.value).isNull()
        assertThat(store.isFailure.value).isTrue()
    }

    @Test
    fun state_receivedFinishExamAction() {
        dispatcher.dispatch(FinishExamAction.Success(value))
        assertThat(store.recentResult.value).isEqualTo(value)
        assertThat(store.isFailure.value).isFalse()
    }

    @Test
    fun state_receivedFailedFinishExamAction() {
        dispatcher.dispatch(FinishExamAction.Failure(RuntimeException()))
        assertThat(store.recentResult.value).isNull()
        assertThat(store.isFailure.value).isTrue()
    }
}