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

package net.hyakuninanki.reader.ducks.material.model

import android.os.Parcel
import android.os.Parcelable

data class Material(
    val no: Int,
    val noTxt: String,
    val creator: String,
    val kamiNoKuKanji: String,
    val kamiNoKuKana: String,
    val shimoNoKuKanji: String,
    val shimoNoKuKana: String,
    val kimariji: Int,
    val imageNo: String,
    val translation: String,
    val color: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(no)
        parcel.writeString(noTxt)
        parcel.writeString(creator)
        parcel.writeString(kamiNoKuKanji)
        parcel.writeString(kamiNoKuKana)
        parcel.writeString(shimoNoKuKanji)
        parcel.writeString(shimoNoKuKana)
        parcel.writeInt(kimariji)
        parcel.writeString(imageNo)
        parcel.writeString(translation)
        parcel.writeString(color)
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
    }
}
