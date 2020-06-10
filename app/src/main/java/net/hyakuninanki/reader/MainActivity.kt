package net.hyakuninanki.reader

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.analytics.FirebaseAnalytics
import hotchemi.android.rate.AppRate
import net.hyakuninanki.reader.feature.corecomponent.ext.setupActionBar
import net.hyakuninanki.reader.feature.examhistory.di.ExamHistoryComponent
import net.hyakuninanki.reader.feature.exammenu.di.ExamMenuComponent
import net.hyakuninanki.reader.feature.examresult.di.ExamResultComponent
import net.hyakuninanki.reader.feature.examstarter.di.ExamStarterComponent
import net.hyakuninanki.reader.feature.material.di.MaterialComponent
import net.hyakuninanki.reader.feature.question.di.QuestionComponent
import net.hyakuninanki.reader.feature.splash.di.SplashComponent
import net.hyakuninanki.reader.feature.trainingresult.di.TrainingResultComponent
import net.hyakuninanki.reader.feature.trainingstarter.di.TrainingStarterComponent

class MainActivity : AppCompatActivity(),
    SplashComponent.Provider,
    ExamMenuComponent.Provider,
    MaterialComponent.Provider,
    TrainingStarterComponent.Provider,
    TrainingResultComponent.Provider,
    ExamStarterComponent.Provider,
    ExamResultComponent.Provider,
    ExamHistoryComponent.Provider,
    QuestionComponent.Provider {

    private lateinit var mainComponent: MainComponent
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun splashComponent() = mainComponent.splashComponent()
    override fun materialComponent() = mainComponent.materialListComponent()
    override fun examMenuComponent() = mainComponent.examMenuComponent()
    override fun trainingStarterComponent() = mainComponent.trainingStarterComponent()
    override fun trainingResultComponent() = mainComponent.trainingResultComponent()
    override fun questionFragment() = mainComponent.questionComponent()
    override fun examStarterComponent() = mainComponent.examStarterComponent()
    override fun examResultComponent() = mainComponent.examResultComponent()
    override fun examHistoryComponent() = mainComponent.examHistoryComponent()

    override fun onCreate(savedInstanceState: Bundle?) {
        mainComponent = (application as App).appComponent.mainComponent().create(this)
        mainComponent.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupActionBar(findViewById(R.id.toolbar)) {
        }

        setupNavController()

        setupAnalytics()

        setupAppRate()
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.itemId == android.R.id.home) {
            findNavController(R.id.nav_host_fragment).popBackStack()
            return false
        }
        return super.onOptionsItemSelected(menuItem)
    }

    private fun setupNavController() {
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
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

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.splash_fragment -> {
                    supportActionBar?.setDisplayHomeAsUpEnabled(false)
                    navView.visibility = View.GONE
                }
                R.id.navigation_training_starter,
                R.id.navigation_training_result,
                R.id.navigation_exam_finisher,
                R.id.navigation_exam_result,
                R.id.navigation_exam_history,
                R.id.navigation_question,
                R.id.navigation_question_answer,
                R.id.navigation_material_detail,
                R.id.navigation_material_detail_page -> {
                    navView.visibility = View.GONE
                }
                else -> {
                    navView.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun setupAppRate() {
        AppRate.with(this)
            .setInstallDays(1)
            .setLaunchTimes(3)
            .setRemindInterval(10)
            .setShowLaterButton(true)
            .setDebug(BuildConfig.DEBUG)
            .monitor()

        AppRate.showRateDialogIfMeetsConditions(this)
    }

    private fun setupAnalytics() {
        firebaseAnalytics = FirebaseAnalytics.getInstance(this)
    }
}
