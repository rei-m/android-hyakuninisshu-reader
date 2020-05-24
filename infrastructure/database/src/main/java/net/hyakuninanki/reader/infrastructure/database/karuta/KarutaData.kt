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

package net.hyakuninanki.reader.infrastructure.database.karuta

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "karuta_table")
class KarutaData(
    @PrimaryKey(autoGenerate = false) @ColumnInfo(index = true) @Json(name = "no") var no: String,
    @Json(name = "creator") var creator: String,
    @ColumnInfo(name = "first_kana") @Json(name = "first_kana") var firstKana: String,
    @ColumnInfo(name = "first_kanji") @Json(name = "first_kanji") var firstKanji: String,
    @ColumnInfo(name = "second_kana") @Json(name = "second_kana") var secondKana: String,
    @ColumnInfo(name = "second_kanji") @Json(name = "second_kanji") var secondKanji: String,
    @ColumnInfo(name = "third_kana") @Json(name = "third_kana") var thirdKana: String,
    @ColumnInfo(name = "third_kanji") @Json(name = "third_kanji") var thirdKanji: String,
    @ColumnInfo(name = "fourth_kana") @Json(name = "fourth_kana") var fourthKana: String,
    @ColumnInfo(name = "fourth_kanji") @Json(name = "fourth_kanji") var fourthKanji: String,
    @ColumnInfo(name = "fifth_kana") @Json(name = "fifth_kana") var fifthKana: String,
    @ColumnInfo(name = "fifth_kanji") @Json(name = "fifth_kanji") var fifthKanji: String,
    @Json(name = "kimariji") var kimariji: String,
    @Json(name = "translation") var translation: String,
    @Json(name = "color") var color: String,
    @ColumnInfo(name = "color_no") @Json(name = "color_no") var colorNo: Int,
    @ColumnInfo(name = "torifuda") @Json(name = "torifuda") var torifuda: String
)
//    @PrimaryKey(autoGenerate = true)
//    private val id = 0

//@Setter("creator") @Column @Json(name = "creator") var creator: String,
//@Setter("firstKana") @Column @Json(name = "first_kana") var firstKana: String,
//@Setter("firstKanji") @Column @Json(name = "first_kanji") var firstKanji: String,
//@Setter("secondKana") @Column @Json(name = "second_kana") var secondKana: String,
//@Setter("secondKanji") @Column @Json(name = "second_kanji") var secondKanji: String,
//@Setter("thirdKana") @Column @Json(name = "third_kana") var thirdKana: String,
//@Setter("thirdKanji") @Column @Json(name = "third_kanji") var thirdKanji: String,
//@Setter("fourthKana") @Column @Json(name = "fourth_kana") var fourthKana: String,
//@Setter("fourthKanji") @Column @Json(name = "fourth_kanji") var fourthKanji: String,
//@Setter("fifthKana") @Column @Json(name = "fifth_kana") var fifthKana: String,
//@Setter("fifthKanji") @Column @Json(name = "fifth_kanji") var fifthKanji: String,
//@Setter("kimariji") @Column @Json(name = "kimariji") var kimariji: Int,
//@Setter("imageNo") @Column @Json(name = "image_no") var imageNo: String,
//@Setter("translation") @Column @Json(name = "translation") var translation: String,
//@Setter("color") @Column @Json(name = "color") var color: String,
//@Setter("colorNo") @Column @Json(name = "color_no") var colorNo: Int,