package net.hyakuninanki.reader

import android.app.Application
import net.hyakuninanki.reader.di.AppComponent
import net.hyakuninanki.reader.di.DaggerAppComponent
import timber.log.Timber

open class App : Application() {

    // Instance of the AppComponent that will be used by all the Activities in the project
    val appComponent: AppComponent by lazy {
        // Creates an instance of AppComponent using its Factory constructor
        // We pass the applicationContext that will be used as Context in the graph
//        DaggerAppComponent.factory().create(applicationContext)
        initializeComponent()
    }

    open fun initializeComponent(): AppComponent {
        return DaggerAppComponent.factory().create(applicationContext)
    }

    override fun onCreate() {
        super.onCreate()
        initTimber()
//        initAdMob()
    }

    protected open fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
//            Timber.plant(CrashlyticsTree())
        }
    }

//    protected open fun initAdMob() {
//        MobileAds.initialize(this) {
//            Timber.i("initialize Ad")
//        }
//    }
}