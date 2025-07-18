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

package net.hyakuninanki.reader.feature.question.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import net.hyakuninanki.reader.feature.question.databinding.QuestionFragmentBinding
import net.hyakuninanki.reader.state.question.model.QuestionState
import javax.inject.Inject

@AndroidEntryPoint
class QuestionFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: QuestionViewModel.Factory

    private val args: QuestionFragmentArgs by navArgs()

    private var _binding: QuestionFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<QuestionViewModel> {
        viewModelFactory.apply {
            questionId = args.questionId
            displayMode = args.displayMode
            inputSecond = args.inputSecond
            canReplay = args.referer.canReplayReader
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = QuestionFragmentBinding.inflate(inflater, container, false)
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
        binding.layoutQuestionResult.setOnClickListener {
            val state = viewModel.state.value
            if (state is QuestionState.Answered) {
                val action =
                    QuestionFragmentDirections.actionQuestionToAnswer(
                        nextQuestionId = state.nextQuestionId,
                        correctKaruta = state.correctMaterial,
                        displayMode = args.displayMode,
                        inputSecond = args.inputSecond,
                        referer = args.referer,
                    )
                findNavController().navigate(action)
            }
        }
    }
}
