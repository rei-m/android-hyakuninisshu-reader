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
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import net.hyakuninanki.reader.feature.material.databinding.MaterialListFragmentBinding
import net.hyakuninanki.reader.state.material.model.ColorFilter
import javax.inject.Inject

@AndroidEntryPoint
class MaterialListFragment : Fragment(), MaterialListAdapter.OnItemInteractionListener {
    @Inject
    lateinit var viewModelFactory: MaterialViewModel.Factory

    private var _binding: MaterialListFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel by activityViewModels<MaterialViewModel> { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MaterialListFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        with(binding.recyclerMaterialList) {
            adapter = MaterialListAdapter(requireContext(), listOf(), this@MaterialListFragment)
            addItemDecoration(
                DividerItemDecoration(
                    inflater.context,
                    DividerItemDecoration.VERTICAL
                )
            )
        }

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.materialList.observe(viewLifecycleOwner, Observer {
            it ?: return@Observer
            (binding.recyclerMaterialList.adapter as MaterialListAdapter).replaceData(it)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        for (colorFilter in ColorFilter.values()) {
            val menuItem =
                menu.add(Menu.NONE, colorFilter.ordinal, Menu.NONE, colorFilter.resId)
            menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER)
            menuItem.setOnMenuItemClickListener {
                viewModel.colorFilter = colorFilter
                false
            }
        }
    }

    override fun onItemClicked(position: Int) {
        val action = MaterialListFragmentDirections.actionMaterialListToMaterialDetail(
            position = position
        )
        findNavController().navigate(action)
    }
}
