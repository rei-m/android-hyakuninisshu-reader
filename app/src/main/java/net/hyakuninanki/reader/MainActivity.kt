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
import net.hyakuninanki.reader.feature.materiallist.di.MaterialListComponent
import net.hyakuninanki.reader.feature.materiallist.di.MaterialListComponentProvider
import net.hyakuninanki.reader.feature.splash.di.SplashComponent
import net.hyakuninanki.reader.feature.splash.di.SplashComponentProvider

class MainActivity : AppCompatActivity(), SplashComponentProvider, MaterialListComponentProvider {

    private lateinit var _splashComponent: SplashComponent
    override fun splashComponent(): SplashComponent = _splashComponent

    private lateinit var _materialListComponent: MaterialListComponent
    override fun materialListComponent(): MaterialListComponent = _materialListComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        val mainComponent = (application as App).appComponent.mainComponent().create()
        _splashComponent = mainComponent.splashComponent().create()
        _materialListComponent = mainComponent.materialListComponent().create()

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
                R.id.navigation_training_menu,
                R.id.navigation_material_list,
                R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.splash_fragment) {
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
                navView.visibility = View.GONE
            } else {
                navView.visibility = View.VISIBLE
            }
        }
    }
}
