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

package net.hyakuninanki.reader.feature.examhistory.ui.widget

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import net.hyakuninanki.reader.feature.examhistory.databinding.AdapterItemExamResultBinding
import net.hyakuninanki.reader.state.exam.model.ExamResult

class ExamResultListAdapter(
    context: Context,
    private var examResultList: List<ExamResult>
) : RecyclerView.Adapter<ExamResultListAdapter.ItemViewHolder>() {

    private val itemPaddingBottom = 0

    // TODO
//    private val lastItemPaddingBottom = context.adHeight
    private val lastItemPaddingBottom = itemPaddingBottom

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = AdapterItemExamResultBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        with(holder.binding) {
            val paddingBottom = if (position == examResultList.lastIndex) {
                lastItemPaddingBottom
            } else {
                itemPaddingBottom
            }
            holder.binding.layoutRoot.setPadding(
                holder.binding.layoutRoot.paddingLeft,
                holder.binding.layoutRoot.paddingTop,
                holder.binding.layoutRoot.paddingRight,
                paddingBottom
            )
            examResult = examResultList[position]
            executePendingBindings()
        }
    }

    override fun getItemCount() = examResultList.size

    fun replaceData(examResultList: List<ExamResult>) {
        this.examResultList = examResultList
        notifyDataSetChanged()
    }

    class ItemViewHolder(
        val binding: AdapterItemExamResultBinding
    ) : RecyclerView.ViewHolder(binding.root)
}
