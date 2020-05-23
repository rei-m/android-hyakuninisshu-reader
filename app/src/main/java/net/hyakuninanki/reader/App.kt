package net.hyakuninanki.reader

import android.app.Application
import timber.log.Timber

open class App : Application() {

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