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

package net.hyakuninanki.reader.domain.question.service

import net.hyakuninanki.reader.domain.helper.TestHelper
import net.hyakuninanki.reader.domain.karuta.model.KarutaNo
import net.hyakuninanki.reader.domain.karuta.model.KarutaNoCollection
import net.hyakuninanki.reader.domain.question.model.Question
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

class CreateQuestionListServiceTest {
    @RunWith(RobolectricTestRunner::class)
    class WhenSuccess : TestHelper {
        @Test
        fun execution() {
            val actual =
                CreateQuestionListService()(
                    allKarutaNoCollection = allKarutaNoCollection,
                    targetKarutaNoCollection = KarutaNoCollection(listOf(KarutaNo(1))),
                    choiceSize = 9,
                )
            assertThat(actual.size).isEqualTo(1)
            assertThat(actual[0].no).isEqualTo(1)
            assertThat(actual[0].choiceList).contains(KarutaNo(1))
            assertThat(actual[0].choiceList.size).isEqualTo(9)
            assertThat(actual[0].correctNo).isEqualTo(KarutaNo(1))
            assertThat(actual[0].state).isInstanceOf(Question.State.Ready::class.java)
        }
    }

    class WhenLackKarutaMaster : TestHelper {
        @Test(expected = IllegalArgumentException::class)
        fun execution() {
            CreateQuestionListService()(
                allKarutaNoCollection =
                    KarutaNoCollection(
                        allKarutaNoCollection.values.subList(
                            0,
                            99,
                        ),
                    ),
                targetKarutaNoCollection = KarutaNoCollection(listOf(KarutaNo(1))),
                choiceSize = 9,
            )
        }
    }

    class WhenTargetIsEmpty : TestHelper {
        @Test(expected = IllegalArgumentException::class)
        fun execution() {
            CreateQuestionListService()(
                allKarutaNoCollection = allKarutaNoCollection,
                targetKarutaNoCollection = KarutaNoCollection(listOf()),
                choiceSize = 9,
            )
        }
    }

    companion object {
        private val allKarutaNoCollection = KarutaNoCollection(KarutaNo.LIST)
    }
}
