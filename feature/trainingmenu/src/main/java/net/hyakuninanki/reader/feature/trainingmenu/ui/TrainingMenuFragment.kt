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

package net.hyakuninanki.reader.feature.trainingmenu.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import net.hyakuninanki.reader.feature.corecomponent.ext.setUp
import net.hyakuninanki.reader.feature.corecomponent.ext.setUpDropDown
import net.hyakuninanki.reader.feature.trainingmenu.R
import net.hyakuninanki.reader.feature.trainingmenu.databinding.TrainingMenuFragmentBinding
import net.hyakuninanki.reader.state.training.model.*

class TrainingMenuFragment : Fragment() {

    private var _binding: TrainingMenuFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel by activityViewModels<TrainingMenuViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = TrainingMenuFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.dropdownRangeFrom.setUpDropDown(
            RangeFromCondition.values().map { it.label(resources) }
        )

        binding.dropdownRangeTo.setUpDropDown(
            RangeToCondition.values().map { it.label(resources) }
        )
        binding.dropdownKimariji.setUpDropDown(
            KimarijiCondition.values().map { it.label(resources) }
        )
        binding.dropdownColor.setUpDropDown(
            ColorCondition.values().map { it.label(resources) }
        )
        binding.dropdownDisplayMode.setUpDropDown(
            DisplayModeCondition.values().map { it.label(resources) }
        )
        binding.dropdownInputSecond.setUpDropDown(
            InputSecondCondition.values().map { it.label(resources) }
        )

        binding.buttonStartTraining.setOnClickListener {
            if (viewModel.rangeTo.ordinal < viewModel.rangeFrom.ordinal) {
                Snackbar.make(
                    binding.root,
                    R.string.text_message_invalid_training_range,
                    Snackbar.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            val action = TrainingMenuFragmentDirections.actionTrainingMenuToTrainingStarter(
                rangeFrom = viewModel.rangeFrom,
                rangeTo = viewModel.rangeTo,
                kimariji = viewModel.kimariji,
                color = viewModel.color,
                displayMode = viewModel.displayMode,
                inputSecond = viewModel.inputSecond
            )
            findNavController().navigate(action)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.dropdownRangeFrom.setUp(viewModel.rangeFrom.label(resources)) {
            viewModel.rangeFrom = RangeFromCondition.values()[it]
        }

        binding.dropdownRangeTo.setUp(viewModel.rangeTo.label(resources)) {
            viewModel.rangeTo = RangeToCondition.values()[it]
        }

        binding.dropdownKimariji.setUp(viewModel.kimariji.label(resources)) {
            viewModel.kimariji = KimarijiCondition.values()[it]
        }

        binding.dropdownColor.setUp(viewModel.color.label(resources)) {
            viewModel.color = ColorCondition.values()[it]
        }
        binding.dropdownDisplayMode.setUp(viewModel.displayMode.label(resources)) {
            viewModel.displayMode = DisplayModeCondition.values()[it]
        }
        binding.dropdownInputSecond.setUp(viewModel.inputSecond.label(resources)) {
            viewModel.inputSecond = InputSecondCondition.values()[it]
        }
    }
}
