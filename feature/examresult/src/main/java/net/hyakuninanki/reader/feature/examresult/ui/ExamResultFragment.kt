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

package net.hyakuninanki.reader.feature.examresult.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import net.hyakuninanki.reader.feature.examresult.databinding.ExamResultFragmentBinding
import net.hyakuninanki.reader.feature.examresult.ui.widget.ExamResultView
import javax.inject.Inject

@AndroidEntryPoint
class ExamResultFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: ExamResultViewModel.Factory

    private var _binding: ExamResultFragmentBinding? = null
    private val binding get() = _binding!!

    private val args: ExamResultFragmentArgs by navArgs()

    private val viewModel by viewModels<ExamResultViewModel> {
        viewModelFactory.apply {
            examId = args.examId
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = ExamResultFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onDestroyView() {
        binding.viewResult.listener = null
        _binding = null
        super.onDestroyView()
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonBack.setOnClickListener {
            val action = ExamResultFragmentDirections.actionExamResultPop()
            findNavController().navigate(action)
        }
        binding.viewModel = viewModel
        viewModel.materialMap.observe(viewLifecycleOwner) {
            binding.viewResult.listener =
                object : ExamResultView.OnClickItemListener {
                    override fun onClick(karutaNo: Int) {
                        val material = it[karutaNo] ?: return
                        val action =
                            ExamResultFragmentDirections.actionExamResultToMaterialDetailPage(material)
                        findNavController().navigate(action)
                    }
                }
        }
    }
}
