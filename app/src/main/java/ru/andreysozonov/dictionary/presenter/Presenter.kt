package ru.andreysozonov.dictionary.presenter

import ru.andreysozonov.dictionary.model.data.AppState
import ru.andreysozonov.dictionary.view.base.View

interface Presenter<T: AppState, V: View> {

    fun attachView(view: V)
    fun deatchView(view: V)
    fun getData(word: String, isOnline: Boolean)


}