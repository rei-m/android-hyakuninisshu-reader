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

package net.hyakuninanki.reader.feature.question.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import androidx.core.content.res.ResourcesCompat.getDrawable
import androidx.core.content.withStyledAttributes
import net.hyakuninanki.reader.feature.corecomponent.widget.view.VerticalSingleLineTextView
import net.hyakuninanki.reader.feature.question.R
import net.hyakuninanki.reader.state.question.model.ToriFuda
import net.hyakuninanki.reader.state.training.model.DisplayModeCondition

class ToriFudaView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val firstLineView: VerticalSingleLineTextView
    private val secondLineView: VerticalSingleLineTextView
    private val thirdLineView: VerticalSingleLineTextView

    init {
        View.inflate(context, R.layout.torifuda_view, this)

        val padding = resources.getDimensionPixelOffset(R.dimen.spacing_0_5)
        setPadding(padding, padding, padding, padding)
        background = getDrawable(resources, R.drawable.bg_fuda, null)
        foregroundGravity = Gravity.CENTER

        firstLineView = findViewById(R.id.text_torifuda_1)
        secondLineView = findViewById(R.id.text_torifuda_2)
        thirdLineView = findViewById(R.id.text_torifuda_3)

        context.withStyledAttributes(attrs, R.styleable.FudaView) {
            val testSize = getResourceId(R.styleable.FudaView_textSize, R.dimen.text_m)
            firstLineView.setTextSize(testSize)
            secondLineView.setTextSize(testSize)
            thirdLineView.setTextSize(testSize)
        }

        val pad = context.getString(R.string.torifuda_pad)
        firstLineView.drawText(pad)
        secondLineView.drawText(pad)
        thirdLineView.drawText(pad)
    }

    var toriFuda: Pair<ToriFuda, DisplayModeCondition>? = null
        set(value) {
            field = value
            value ?: return
            val (toriFuda, displayMode) = value
            // 札ごとrotateさせるとなぜかelevationが吹き飛ぶので中のテキストをrotateさせている
            if (displayMode == DisplayModeCondition.AITEJIN) {
                firstLineView.drawText(toriFuda.thirdLine)
                secondLineView.drawText(toriFuda.secondLine)
                thirdLineView.drawText(toriFuda.firstLine)
                firstLineView.rotation = 180f
                secondLineView.rotation = 180f
                thirdLineView.rotation = 180f
            } else {
                firstLineView.drawText(toriFuda.firstLine)
                secondLineView.drawText(toriFuda.secondLine)
                thirdLineView.drawText(toriFuda.thirdLine)
            }
        }
}
