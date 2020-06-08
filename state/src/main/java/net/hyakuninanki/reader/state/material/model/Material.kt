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

package net.hyakuninanki.reader.state.material.model

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import net.hyakuninanki.reader.domain.karuta.model.Karuta
import net.hyakuninanki.reader.state.core.ext.imageResId
import net.hyakuninanki.reader.state.core.ext.rawResId
import net.hyakuninanki.reader.state.core.ext.toText

data class Material(
    val noTxt: String,
    val kimariji: Int,
    val kimarijiTxt: String,
    val creator: String,
    val shokuKanji: String,
    val shokuKana: String,
    val nikuKanji: String,
    val nikuKana: String,
    val sankuKanji: String,
    val sankuKana: String,
    val shikuKanji: String,
    val shikuKana: String,
    val gokuKanji: String,
    val gokuKana: String,
    val translation: String,
    @DrawableRes val imageResId: Int,
    @RawRes val rawResId: Int
) : Parcelable {

    val kamiNoKuKanji: String = "$shokuKanji　$nikuKanji　$sankuKanji"
    val kamiNoKuKana: String = "$shokuKana　$nikuKana　$sankuKana"
    val shimoNoKuKanji: String = "$nikuKanji　$sankuKanji"
    val shimoNoKuKana: String = "$nikuKana　$sankuKana"

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(noTxt)
        parcel.writeInt(kimariji)
        parcel.writeString(kimarijiTxt)
        parcel.writeString(creator)
        parcel.writeString(shokuKanji)
        parcel.writeString(shokuKana)
        parcel.writeString(nikuKanji)
        parcel.writeString(nikuKana)
        parcel.writeString(sankuKanji)
        parcel.writeString(sankuKana)
        parcel.writeString(shikuKanji)
        parcel.writeString(shikuKana)
        parcel.writeString(gokuKanji)
        parcel.writeString(gokuKana)
        parcel.writeString(translation)
        parcel.writeInt(imageResId)
        parcel.writeInt(rawResId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Material> {
        override fun createFromParcel(parcel: Parcel): Material {
            return Material(parcel)
        }

        override fun newArray(size: Int): Array<Material?> {
            return arrayOfNulls(size)
        }

        fun createFromKaruta(karuta: Karuta, context: Context): Material {
            return Material(
                noTxt = karuta.no.toText(context),
                kimariji = karuta.kimariji.value,
                kimarijiTxt = karuta.kimariji.toText(context),
                creator = karuta.creator,
                shokuKanji = karuta.kamiNoKu.shoku.kanji,
                shokuKana = karuta.kamiNoKu.shoku.kana,
                nikuKanji = karuta.kamiNoKu.niku.kanji,
                nikuKana = karuta.kamiNoKu.niku.kana,
                sankuKanji = karuta.kamiNoKu.sanku.kanji,
                sankuKana = karuta.kamiNoKu.sanku.kana,
                shikuKanji = karuta.shimoNoKu.shiku.kanji,
                shikuKana = karuta.shimoNoKu.shiku.kana,
                gokuKanji = karuta.shimoNoKu.goku.kanji,
                gokuKana = karuta.shimoNoKu.goku.kana,
                translation = karuta.translation,
                imageResId = karuta.imageResId(context),
                rawResId = karuta.rawResId(context)
            )
        }
    }
}