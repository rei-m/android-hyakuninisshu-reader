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

package net.hyakuninanki.reader.feature.material.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.adapter.FragmentStateAdapter
import net.hyakuninanki.reader.feature.material.databinding.MaterialDetailFragmentBinding
import net.hyakuninanki.reader.feature.material.di.MaterialComponent
import net.hyakuninanki.reader.state.material.model.Material
import javax.inject.Inject

class MaterialDetailFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: MaterialViewModel.Factory

    private val args: MaterialDetailFragmentArgs by navArgs()

    private var _binding: MaterialDetailFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel by activityViewModels<MaterialViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        (requireActivity() as MaterialComponent.Provider)
            .materialComponent()
            .create()
            .inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MaterialDetailFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        val pagerAdapter = ScreenSlidePagerAdapter(this, listOf())
        binding.pager.adapter = pagerAdapter

        return binding.root
    }

    override fun onDestroyView() {
        binding.pager.adapter = null
        _binding = null
        super.onDestroyView()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.materialList.observe(viewLifecycleOwner, Observer {
            it ?: return@Observer
            (binding.pager.adapter as ScreenSlidePagerAdapter).replaceData(it)
            if (savedInstanceState == null) {
                binding.pager.setCurrentItem(args.position, false)
            } else {
                binding.pager.setCurrentItem(savedInstanceState.getInt(ARG_POSITION, 0), false)
            }
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(ARG_POSITION, binding.pager.currentItem)
        super.onSaveInstanceState(outState)
    }

    private inner class ScreenSlidePagerAdapter(
        fragment: Fragment,
        private var materialList: List<Material>
    ) : FragmentStateAdapter(fragment) {
        override fun getItemCount(): Int = materialList.size
        override fun createFragment(position: Int): Fragment =
            MaterialDetailPageFragment.newInstance(materialList[position])

        fun replaceData(materialList: List<Material>) {
            this.materialList = materialList
            notifyDataSetChanged()
        }
    }

    companion object {
        private const val ARG_POSITION = "ARG_POSITION"
    }
}
