package ru.andreysozonov.dictionary.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ru.andreysozonov.dictionary.view.main.MainActivity


@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity
}