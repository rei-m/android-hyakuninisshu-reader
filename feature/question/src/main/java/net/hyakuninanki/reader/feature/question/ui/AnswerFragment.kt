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

package net.hyakuninanki.reader.feature.question.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import net.hyakuninanki.reader.feature.question.databinding.AnswerFragmentBinding
import net.hyakuninanki.reader.feature.question.di.QuestionComponent

class AnswerFragment : Fragment() {
    private var _binding: AnswerFragmentBinding? = null
    private val binding get() = _binding!!

    private val args: AnswerFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        (requireActivity() as QuestionComponent.Provider)
            .questionFragment()
            .create()
            .inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AnswerFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.material = args.correctKaruta
        binding.existNextQuiz = args.nextQuestionId != null
        binding.buttonGoToNext.setOnClickListener {
            args.nextQuestionId?.let {
                val action = AnswerFragmentDirections.actionAnswerToQuestion(
                    questionId = it,
                    inputSecond = args.inputSecond
                )
                findNavController().navigate(action)
            }
        }
        binding.buttonGoToResult.setOnClickListener {
            // TODO: exam resultと分岐が必要
            val action = AnswerFragmentDirections.actionAnswerToTrainingResult(
                inputSecond = args.inputSecond
            )
            findNavController().navigate(action)
        }
        binding.textMaterial.setOnClickListener {
            val action = AnswerFragmentDirections.actionAnswerToMaterialDetailPage(
                material = args.correctKaruta
            )
            findNavController().navigate(action)
        }
    }
}
