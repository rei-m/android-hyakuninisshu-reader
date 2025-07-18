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

package net.hyakuninanki.reader.state.core.ext

import android.content.Context
import net.hyakuninanki.reader.domain.karuta.model.Karuta
import net.hyakuninanki.reader.domain.karuta.model.KarutaNo
import net.hyakuninanki.reader.domain.question.model.Exam
import net.hyakuninanki.reader.state.R
import net.hyakuninanki.reader.state.exam.model.ExamResult
import net.hyakuninanki.reader.state.material.model.Material
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

private val RAW_ID_LIST =
    arrayOf(
        R.raw.karuta_001,
        R.raw.karuta_002,
        R.raw.karuta_003,
        R.raw.karuta_004,
        R.raw.karuta_005,
        R.raw.karuta_006,
        R.raw.karuta_007,
        R.raw.karuta_008,
        R.raw.karuta_009,
        R.raw.karuta_010,
        R.raw.karuta_011,
        R.raw.karuta_012,
        R.raw.karuta_013,
        R.raw.karuta_014,
        R.raw.karuta_015,
        R.raw.karuta_016,
        R.raw.karuta_017,
        R.raw.karuta_018,
        R.raw.karuta_019,
        R.raw.karuta_020,
        R.raw.karuta_021,
        R.raw.karuta_022,
        R.raw.karuta_023,
        R.raw.karuta_024,
        R.raw.karuta_025,
        R.raw.karuta_026,
        R.raw.karuta_027,
        R.raw.karuta_028,
        R.raw.karuta_029,
        R.raw.karuta_030,
        R.raw.karuta_031,
        R.raw.karuta_032,
        R.raw.karuta_033,
        R.raw.karuta_034,
        R.raw.karuta_035,
        R.raw.karuta_036,
        R.raw.karuta_037,
        R.raw.karuta_038,
        R.raw.karuta_039,
        R.raw.karuta_040,
        R.raw.karuta_041,
        R.raw.karuta_042,
        R.raw.karuta_043,
        R.raw.karuta_044,
        R.raw.karuta_045,
        R.raw.karuta_046,
        R.raw.karuta_047,
        R.raw.karuta_048,
        R.raw.karuta_049,
        R.raw.karuta_050,
        R.raw.karuta_051,
        R.raw.karuta_052,
        R.raw.karuta_053,
        R.raw.karuta_054,
        R.raw.karuta_055,
        R.raw.karuta_056,
        R.raw.karuta_057,
        R.raw.karuta_058,
        R.raw.karuta_059,
        R.raw.karuta_060,
        R.raw.karuta_061,
        R.raw.karuta_062,
        R.raw.karuta_063,
        R.raw.karuta_064,
        R.raw.karuta_065,
        R.raw.karuta_066,
        R.raw.karuta_067,
        R.raw.karuta_068,
        R.raw.karuta_069,
        R.raw.karuta_070,
        R.raw.karuta_071,
        R.raw.karuta_072,
        R.raw.karuta_073,
        R.raw.karuta_074,
        R.raw.karuta_075,
        R.raw.karuta_076,
        R.raw.karuta_077,
        R.raw.karuta_078,
        R.raw.karuta_079,
        R.raw.karuta_080,
        R.raw.karuta_081,
        R.raw.karuta_082,
        R.raw.karuta_083,
        R.raw.karuta_084,
        R.raw.karuta_085,
        R.raw.karuta_086,
        R.raw.karuta_087,
        R.raw.karuta_088,
        R.raw.karuta_089,
        R.raw.karuta_090,
        R.raw.karuta_091,
        R.raw.karuta_092,
        R.raw.karuta_093,
        R.raw.karuta_094,
        R.raw.karuta_095,
        R.raw.karuta_096,
        R.raw.karuta_097,
        R.raw.karuta_098,
        R.raw.karuta_099,
        R.raw.karuta_100,
    )

fun Karuta.rawResId(): Int = RAW_ID_LIST[no.value - 1]

fun Karuta.toMaterial(context: Context): Material {
    val resources = context.resources

    val kimarijiArray = resources.getStringArray(R.array.kimariji)
    val kimarijiTxt = kimarijiArray[kimariji.value - 1]

    val typedArray = resources.obtainTypedArray(R.array.karuta_image)
    val imageResId = typedArray.getResourceId(no.value - 1, 0)
    typedArray.recycle()

    return Material(
        no = no.value,
        noTxt = no.toText(context),
        kimariji = kimariji.value,
        kimarijiTxt = kimarijiTxt,
        creator = creator,
        shokuKanji = kamiNoKu.shoku.kanji,
        shokuKana = kamiNoKu.shoku.kana,
        nikuKanji = kamiNoKu.niku.kanji,
        nikuKana = kamiNoKu.niku.kana,
        sankuKanji = kamiNoKu.sanku.kanji,
        sankuKana = kamiNoKu.sanku.kana,
        shikuKanji = shimoNoKu.shiku.kanji,
        shikuKana = shimoNoKu.shiku.kana,
        gokuKanji = shimoNoKu.goku.kanji,
        gokuKana = shimoNoKu.goku.kana,
        translation = translation,
        imageResId = imageResId,
        rawResId = rawResId(),
    )
}

fun Exam.toResult(
    context: Context,
    now: Date,
): ExamResult {
    val averageAnswerTimeString =
        String.format(
            Locale.JAPAN,
            "%.2f",
            result.resultSummary.averageAnswerSec,
        )

    return ExamResult(
        id = id.value,
        score = result.resultSummary.score,
        averageAnswerSecText = context.getString(R.string.seconds, averageAnswerTimeString),
        questionResultList =
            KarutaNo.LIST.map { karutaNo ->
                QuestionResult(
                    karutaNo = karutaNo.value,
                    karutaNoText = karutaNo.toText(context),
                    isCorrect = !result.wrongKarutaNoCollection.contains(karutaNo),
                )
            },
        fromNowText = tookDate.diffString(context, now),
    )
}
