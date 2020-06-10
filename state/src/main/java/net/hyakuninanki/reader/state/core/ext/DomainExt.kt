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

package net.hyakuninanki.reader.state.core.ext

import android.content.Context
import net.hyakuninanki.reader.domain.karuta.model.Karuta
import net.hyakuninanki.reader.domain.karuta.model.KarutaNo
import net.hyakuninanki.reader.domain.karuta.model.Kimariji
import net.hyakuninanki.reader.domain.question.model.KarutaExam
import net.hyakuninanki.reader.state.BuildConfig
import net.hyakuninanki.reader.state.R
import net.hyakuninanki.reader.state.exam.model.ExamResult
import net.hyakuninanki.reader.state.question.model.QuestionResult
import java.util.*

fun KarutaNo.toText(context: Context): String {
    if (this.value == 100) {
        return context.getString(R.string.karuta_number, context.getString(R.string.hundred))
    }

    val resources = context.resources
    val numArray = resources.getStringArray(R.array.number)

    val doubleDigits = this.value / 10
    val singleDigits = this.value % 10

    val sb = StringBuilder()
    if (0 < doubleDigits) {
        if (1 < doubleDigits) {
            sb.append(numArray[doubleDigits - 1])
        }
        sb.append(resources.getString(R.string.ten))
    }

    if (0 < singleDigits) {
        sb.append(numArray[singleDigits - 1])
    }

    return context.getString(R.string.karuta_number, sb.toString())
}

fun Kimariji.toText(context: Context): String {
    val resources = context.resources
    val kimarijiArray = resources.getStringArray(R.array.kimariji)
    return kimarijiArray[this.value - 1]
}

fun Karuta.rawResId(context: Context): Int {
    val rawResName = if (BuildConfig.DEBUG) "000" else this.imageNo.value
    return context.resources.getIdentifier(
        "karuta_${rawResName}",
        "raw",
        context.packageName
    )
}

fun Karuta.imageResId(context: Context): Int {
    return context.resources.getIdentifier(
        "karuta_${imageNo.value}",
        "drawable",
        context.packageName
    )
}

fun KarutaExam.toResult(context: Context, now: Date): ExamResult {
    val averageAnswerTimeString = String.format(
        Locale.JAPAN,
        "%.2f",
        result.resultSummary.averageAnswerSec
    )

    return ExamResult(
        id = id.value,
        score = result.resultSummary.score,
        averageAnswerSecText = context.getString(R.string.seconds, averageAnswerTimeString),
        questionResultList = KarutaNo.LIST.map { karutaNo ->
            QuestionResult(
                karutaNo = karutaNo.value,
                karutaNoText = karutaNo.toText(context),
                isCorrect = !result.wrongKarutaNoCollection.contains(karutaNo)
            )
        },
        fromNowText = tookDate.diffString(context, now)
    )
}
