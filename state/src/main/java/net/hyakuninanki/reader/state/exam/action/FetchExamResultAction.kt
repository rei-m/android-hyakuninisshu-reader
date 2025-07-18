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

package net.hyakuninanki.reader.state.exam.action

import net.hyakuninanki.reader.state.core.Action
import net.hyakuninanki.reader.state.exam.model.ExamResult
import net.hyakuninanki.reader.state.material.model.Material

/**
 * 力試しの結果を取得するアクション.
 */
sealed class FetchExamResultAction(
    override val error: Throwable? = null,
) : Action {
    class Success(
        val examResult: ExamResult,
        val materialList: List<Material>,
    ) : FetchExamResultAction() {
        override fun toString() = "$name(examResult=$examResult, materialList=$materialList)"
    }

    class Failure(
        error: Throwable,
    ) : FetchExamResultAction(error) {
        override fun toString() = "$name(error=$error)"
    }

    override val name = "FetchExamResultAction"
}
