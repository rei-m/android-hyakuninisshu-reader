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

package net.hyakuninanki.reader.feature.trainingresult.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import net.hyakuninanki.reader.feature.trainingresult.databinding.TrainingResultFragmentBinding
import javax.inject.Inject

@AndroidEntryPoint
class TrainingResultFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: TrainingResultViewModel.Factory

    private var _binding: TrainingResultFragmentBinding? = null
    private val binding get() = _binding!!

    private val args: TrainingResultFragmentArgs by navArgs()

    private val viewModel by viewModels<TrainingResultViewModel> { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = TrainingResultFragmentBinding.inflate(inflater, container, false)
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
        binding.buttonRestart.setOnClickListener {
            val action =
                TrainingResultFragmentDirections.actionTrainingResultToTrainingReStarter(
                    displayMode = args.displayMode,
                    inputSecond = args.inputSecond,
                )
            findNavController().navigate(action)
        }
        binding.buttonBack.setOnClickListener {
            val action = TrainingResultFragmentDirections.actionTrainingResultPop()
            findNavController().navigate(action)
        }
        binding.viewModel = viewModel
    }
}
