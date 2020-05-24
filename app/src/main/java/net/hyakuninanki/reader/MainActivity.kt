package net.hyakuninanki.reader

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import net.hyakuninanki.reader.feature.corecomponent.ext.setupActionBar
import net.hyakuninanki.reader.feature.splash.di.SplashComponent
import net.hyakuninanki.reader.feature.splash.di.SplashComponentProvider

class MainActivity : AppCompatActivity(), SplashComponentProvider {

    private lateinit var _splashComponent: SplashComponent
    override fun splashComponent(): SplashComponent = _splashComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        val mainComponent = (application as App).appComponent.mainComponent().create()
        _splashComponent = mainComponent.splashComponent().create()
        mainComponent.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupActionBar(findViewById(R.id.toolbar)) {
        }

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.splash_fragment) {
                navView.visibility = View.GONE
            } else {
                navView.visibility = View.VISIBLE
            }
        }
    }
}
