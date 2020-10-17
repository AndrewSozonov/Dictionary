package ru.andreysozonov.dictionary.application


import android.app.Application
import org.koin.core.context.startKoin
import ru.andreysozonov.dictionary.di.application
import ru.andreysozonov.dictionary.di.mainScreen


class DictionaryApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(listOf(application, mainScreen))
        }
    }
}