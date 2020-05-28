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

package net.hyakuninanki.reader.domain.question

import android.os.Parcel
import android.os.Parcelable
import net.hyakuninanki.reader.domain.EntityId
import java.util.*

data class QuestionId(
    val value: String = UUID.randomUUID().toString()
) : EntityId, Parcelable {

    override fun describeContents(): Int = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(this.value)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<QuestionId> = object : Parcelable.Creator<QuestionId> {
            override fun createFromParcel(source: Parcel): QuestionId =
                QuestionId(source.readString()!!)

            override fun newArray(size: Int): Array<QuestionId?> = arrayOfNulls(size)
        }
    }
}
