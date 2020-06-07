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

package net.hyakuninanki.reader.domain.karuta.model

/**
 * 歌リポジトリ.
 */
interface KarutaRepository {

    /**
     * 歌セットを初期化する.
     */
    suspend fun initialize()

    /**
     * 指定した番号の歌を取得する
     */
    suspend fun findByNo(
        karutaNo: KarutaNo
    ): Karuta

    /**
     * 歌のリストを取得する
     */
    suspend fun findAllWithCondition(
        fromNo: KarutaNo,
        toNo: KarutaNo,
        kimarijis: List<Kimariji>,
        colors: List<KarutaColor>
    ): List<Karuta>

    /**
     * 指定した番号の歌のリストを取得する
     */
    suspend fun findAllWithNo(
        karutaNoList: List<KarutaNo>
    ): List<Karuta>
}
