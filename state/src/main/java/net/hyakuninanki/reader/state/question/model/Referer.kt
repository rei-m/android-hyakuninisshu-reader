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

package net.hyakuninanki.reader.state.question.model

/**
 * 練習と力試しのどちらのコンテキストにいるか判定するためのリファラ.
 */
enum class Referer {
    Training {
        override val canReplayReader: Boolean = true
    },
    Exam {
        override val canReplayReader: Boolean = false
    }, ;

    abstract val canReplayReader: Boolean
}
