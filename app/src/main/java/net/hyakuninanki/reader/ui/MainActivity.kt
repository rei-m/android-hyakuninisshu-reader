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

package net.hyakuninanki.reader.ui

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import net.hyakuninanki.reader.R
import net.hyakuninanki.reader.feature.corecomponent.ext.setupActionBar
import net.hyakuninanki.reader.feature.corecomponent.widget.ad.AdViewObserver

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val adViewObserver: AdViewObserver = AdViewObserver()

    private val navController by lazy { findNavController(R.id.nav_host_fragment) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupActionBar(findViewById(R.id.toolbar)) {
        }

        setupNavController()

        setupAd()
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.itemId == android.R.id.home) {
            navController.popBackStack()
            return false
        }
        return super.onOptionsItemSelected(menuItem)
    }

    private fun setupNavController() {
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_training_menu,
                R.id.navigation_exam_menu,
                R.id.navigation_material_list,
                R.id.navigation_support
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val adViewContainer: FrameLayout = findViewById(R.id.ad_view_container)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.splash_fragment -> {
                    supportActionBar?.setDisplayHomeAsUpEnabled(false)
                    navView.visibility = View.GONE
                }
                R.id.navigation_training_starter,
                R.id.navigation_training_re_starter,
                R.id.navigation_exam_practice_training_starter,
                R.id.navigation_exam_finisher,
                R.id.navigation_question,
                R.id.navigation_question_answer -> {
                    navView.visibility = View.GONE
                    adViewObserver.hideAd()
                }
                R.id.navigation_training_result,
                R.id.navigation_exam_result,
                R.id.navigation_exam_history,
                R.id.navigation_material_detail,
                R.id.navigation_material_detail_page -> {
                    navView.visibility = View.GONE
                    adViewObserver.showAd(this, adViewContainer)
                }
                else -> {
                    navView.visibility = View.VISIBLE
                    adViewObserver.showAd(this, adViewContainer)
                }
            }
        }
    }

    private fun setupAd() {
        lifecycle.addObserver(adViewObserver)
    }
}
