package net.hyakuninanki.reader

import android.app.Application
import com.google.android.gms.ads.MobileAds
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
open class App : Application() {

    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate() {
        super.onCreate()
        initTimber()
        initAnalytics()
        initAdMob()
    }

    protected open fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        // FB Crashlytics と連携させる・・・そのうち
    }

    protected open fun initAnalytics() {
        firebaseAnalytics = FirebaseAnalytics.getInstance(this)
    }

    protected open fun initAdMob() {
        MobileAds.initialize(this) {
            Timber.d("initialize AdMob")
        }
    }
}
