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

package net.hyakuninanki.reader.state.question.action

import net.hyakuninanki.reader.state.core.Action
import net.hyakuninanki.reader.state.question.model.QuestionState

sealed class StartAnswerQuestionAction(override val error: Throwable? = null) : Action {

    class Success(val state: QuestionState.InAnswer) : StartAnswerQuestionAction() {
        override fun toString() = "$name(state=$state)"
    }

    class Failure(error: Throwable) : StartAnswerQuestionAction(error) {
        override fun toString() = "$name(error=$error)"
    }

    override val name = "StartAnswerQuestionAction"
}
