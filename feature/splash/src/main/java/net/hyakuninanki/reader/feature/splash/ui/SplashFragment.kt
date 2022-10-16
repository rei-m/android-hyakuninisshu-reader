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

package net.hyakuninanki.reader.feature.splash.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import net.hyakuninanki.reader.feature.corecomponent.helper.EventObserver
import net.hyakuninanki.reader.feature.splash.R
import net.hyakuninanki.reader.feature.splash.databinding.SplashFragmentBinding
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: SplashViewModel.Factory

    private var _binding: SplashFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel by activityViewModels<SplashViewModel> { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SplashFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel

        viewModel.onReadyEvent.observe(viewLifecycleOwner, EventObserver {
            findNavController().navigate(R.id.action_splash_to_trainingMenu)
        })
    }
}
