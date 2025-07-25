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

package net.hyakuninanki.reader.feature.corecomponent.widget.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.annotation.DimenRes
import androidx.core.content.res.ResourcesCompat
import net.hyakuninanki.reader.feature.corecomponent.R

class VerticalSingleLineTextView
    @JvmOverloads
    constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0,
    ) : View(context, attrs, defStyleAttr) {
        private val paint: Paint = Paint()

        private var text: String = ""

        private var textSize: Int = 0

        init {
            initialize(context)
        }

        private fun initialize(context: Context) {
            val resources = context.resources
            text = ""
            textSize = context.resources.getDimensionPixelOffset(R.dimen.text_l)

            with(paint) {
                isAntiAlias = true
                textSize = this@VerticalSingleLineTextView.textSize.toFloat()
                color = ResourcesCompat.getColor(resources, R.color.blackba, null)
                typeface = ResourcesCompat.getFont(context, R.font.hannari)
            }
        }

        fun setTextSize(
            @DimenRes dimenId: Int,
        ) {
            textSize = context.resources.getDimensionPixelOffset(dimenId)
            paint.textSize = textSize.toFloat()
        }

        fun drawText(text: String?) {
            if (text != null) {
                this.text = text
            }
            val layoutParams = layoutParams
            layoutParams.width = textSize
            layoutParams.height = (textSize.toDouble() * this.text.length.toDouble() * 1.05).toInt()
            setLayoutParams(layoutParams)
            invalidate()
        }

        override fun onDraw(canvas: Canvas) {
            val textLength = text.length
            for (i in 0 until textLength) {
                canvas.drawText(text.substring(i, i + 1), 0f, (textSize * (i + 1)).toFloat(), paint)
            }
        }
    }
