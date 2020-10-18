package ru.andreysozonov.dictionary.application


import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.andreysozonov.dictionary.di.application
import ru.andreysozonov.dictionary.di.historyScreen
import ru.andreysozonov.dictionary.di.mainScreen


class DictionaryApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@DictionaryApp)
            modules(
                listOf(application, mainScreen, historyScreen)
            )
        }
    }
}