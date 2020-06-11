package net.hyakuninanki.reader

import android.app.Application
import com.google.android.gms.ads.MobileAds
import com.google.firebase.analytics.FirebaseAnalytics
import net.hyakuninanki.reader.di.AppComponent
import net.hyakuninanki.reader.di.DaggerAppComponent
import timber.log.Timber

open class App : Application() {

    val appComponent: AppComponent by lazy { initializeComponent() }

    lateinit var firebaseAnalytics: FirebaseAnalytics

    open fun initializeComponent(): AppComponent {
        return DaggerAppComponent.factory().create(applicationContext)
    }

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