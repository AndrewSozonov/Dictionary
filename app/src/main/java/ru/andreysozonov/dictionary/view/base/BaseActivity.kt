package ru.andreysozonov.dictionary.view.base

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import ru.andreysozonov.dictionary.model.data.AppState
import ru.andreysozonov.dictionary.presenter.Presenter

abstract class BaseActivity<T: AppState> : AppCompatActivity(), View{

    lateinit var presenter: Presenter<T, View>
    abstract fun createPresenter(): Presenter<T, View>

    abstract override fun renderData(appstate: AppState)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = createPresenter()
    }

    override fun onStart() {
        super.onStart()
        presenter.attachView(this)
    }

    override fun onStop() {
        super.onStop()
        presenter.deatchView(this)
    }


}