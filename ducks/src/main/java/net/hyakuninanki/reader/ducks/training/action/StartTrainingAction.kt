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

package net.hyakuninanki.reader.ducks.training.action

import net.hyakuninanki.reader.ducks.core.Action

class StartTrainingAction private constructor(
    val questionId: String? = null,
    override val error: Throwable? = null
) : Action {

    val isTargetNotFound = questionId == null

    override val name = "StartTrainingAction"

    override fun toString() = if (isSucceeded) "$name()" else "$name(error=$error)"

    companion object {
        fun createSuccess(questionId: String) = StartTrainingAction(questionId)
        fun createEmpty() = StartTrainingAction()
        fun createError(e: Throwable) = StartTrainingAction(null, e)
    }
}
