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

package net.hyakuninanki.reader.ducks.exam.action

import net.hyakuninanki.reader.ducks.core.Action
import net.hyakuninanki.reader.ducks.exam.model.KarutaExamResult

class FetchRecentExamAction private constructor(
    val karutaExamResult: KarutaExamResult?,
    override val error: Throwable? = null
) : Action {

    override val name = "FetchRecentExamAction"

    override fun toString() =
        if (isSucceeded) "$name(karutaExamResult=$karutaExamResult)" else "$name(error=$error)"

    companion object {
        fun createSuccess(karutaExamResult: KarutaExamResult?) =
            FetchRecentExamAction(karutaExamResult)

        fun createError(error: Throwable) = FetchRecentExamAction(null, error)
    }
}