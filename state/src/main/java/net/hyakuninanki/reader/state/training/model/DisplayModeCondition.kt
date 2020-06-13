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

package net.hyakuninanki.reader.state.training.model

import android.content.res.Resources
import androidx.annotation.StringRes
import net.hyakuninanki.reader.state.R

/**
 * 練習の取り札の表示条件.
 */
enum class DisplayModeCondition(
    val value: Int,
    @param:StringRes private val resId: Int
) : SelectableItem {
    JIJIN(0, R.string.display_mode_jijin),
    AITEJIN(1, R.string.display_mode_aitejin);

    override fun label(res: Resources): String = res.getString(resId)

    companion object {
        operator fun get(ordinal: Int) = values()[ordinal]
    }
}
