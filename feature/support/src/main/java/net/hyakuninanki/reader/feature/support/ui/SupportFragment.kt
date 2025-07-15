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

package net.hyakuninanki.reader.feature.support.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import net.hyakuninanki.reader.feature.support.R
import net.hyakuninanki.reader.feature.support.databinding.SupportFragmentBinding

class SupportFragment : Fragment() {
    private var _binding: SupportFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // TODO: versionName 取れなくなったのでとりあえず
        val versionName = getString(R.string.version, "2.0.0")

        _binding = SupportFragmentBinding.inflate(inflater, container, false)
        binding.textVersion.text = versionName

        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonLicense.setOnClickListener {
            startActivity(Intent(requireContext(), OssLicensesMenuActivity::class.java))
        }
        binding.buttonPrivacyPolicy.setOnClickListener {
            findNavController().navigate(net.hyakuninanki.reader.feature.corecomponent.R.id.action_support_to_privacyPolicyDialog)
        }
        binding.buttonReview.setOnClickListener {
            val intent =
                Intent(
                    Intent.ACTION_VIEW,
                    getString(net.hyakuninanki.reader.feature.corecomponent.R.string.app_url).toUri(),
                )
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            requireActivity().startActivity(intent)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
