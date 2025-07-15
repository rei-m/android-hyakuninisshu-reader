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

package net.hyakuninanki.reader.feature.trainingstarter.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import net.hyakuninanki.reader.feature.corecomponent.helper.EventObserver
import net.hyakuninanki.reader.feature.trainingstarter.databinding.TrainingStarterFragmentBinding
import net.hyakuninanki.reader.state.question.model.Referer
import net.hyakuninanki.reader.state.training.model.DisplayModeCondition
import net.hyakuninanki.reader.state.training.model.InputSecondCondition
import javax.inject.Inject

@AndroidEntryPoint
class ExamPracticeTrainingStarterFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: ExamPracticeTrainingStarterViewModel.Factory

    private var _binding: TrainingStarterFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<ExamPracticeTrainingStarterViewModel> { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = TrainingStarterFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        viewModel.onReadyEvent.observe(
            viewLifecycleOwner,
            EventObserver {
                val action =
                    ExamPracticeTrainingStarterFragmentDirections
                        .actionExamPracticeTrainingStarterToQuestion(
                            questionId = it,
                            inputSecond = InputSecondCondition.SHORT,
                            displayMode = DisplayModeCondition.JIJIN,
                            referer = Referer.Training,
                        )
                findNavController().navigate(action)
            },
        )
    }
}
