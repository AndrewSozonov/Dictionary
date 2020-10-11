package ru.andreysozonov.dictionary.view.base

import androidx.appcompat.app.AppCompatActivity
import ru.andreysozonov.dictionary.model.data.AppState
import ru.andreysozonov.dictionary.view.viewmodel.BaseViewModel
import ru.andreysozonov.dictionary.view.viewmodel.Interactor

abstract class BaseActivity<T: AppState, I: Interactor<T>> : AppCompatActivity(), View{

    abstract val model: BaseViewModel<T>

    abstract override fun renderData(appstate: AppState)
}