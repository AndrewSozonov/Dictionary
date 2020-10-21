package ru.andreysozonov.core.viewmodel

import androidx.appcompat.app.AppCompatActivity
//import ru.andreysozonov.core.View
import ru.andreysozonov.model.data.data.AppState

abstract class BaseActivity<T: ru.andreysozonov.model.data.data.AppState, I: Interactor<T>> : AppCompatActivity() {

    abstract val model: BaseViewModel<T>

    abstract fun renderData(appstate: ru.andreysozonov.model.data.data.AppState)
}


