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

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import net.hyakuninanki.reader.feature.material.databinding.MaterialDetailPageFragmentBinding
import net.hyakuninanki.reader.state.material.model.Material

class MaterialDetailPageFragment : Fragment() {

    private var _binding: MaterialDetailPageFragmentBinding? = null
    private val binding get() = _binding!!

    private val args: MaterialDetailPageFragmentArgs by navArgs()

    private var rawResId: Int = -1
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MaterialDetailPageFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        // ここなくてもいいかも
        val materialFromBundle = requireArguments().getParcelable<Material>(ARG_MATERIAL)
        if (materialFromBundle == null) {
            binding.material = args.material
            rawResId = args.material.rawResId
        } else {
            binding.material = materialFromBundle
            rawResId = materialFromBundle.rawResId
        }

        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonPlayReader.setOnClickListener {
            val targetMediaPlayer = mediaPlayer ?: let {
                val tmp = MediaPlayer.create(context, rawResId)
                mediaPlayer = tmp
                return@let tmp
            }
            if (!targetMediaPlayer.isPlaying) {
                targetMediaPlayer.start()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        mediaPlayer?.stop()
        mediaPlayer = null
    }

    companion object {
        private const val ARG_MATERIAL = "material"

        fun newInstance(material: Material) = MaterialDetailPageFragment().apply {
            arguments = Bundle().apply {
                putParcelable(ARG_MATERIAL, material)
            }
        }
    }
}
