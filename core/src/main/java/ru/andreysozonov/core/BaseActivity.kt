package ru.andreysozonov.core.viewmodel


import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import ru.andreysozonov.core.OnlineLiveData
import ru.andreysozonov.core.R


abstract class BaseActivity<T : ru.andreysozonov.model.data.data.AppState, I : Interactor<T>> :
    AppCompatActivity() {

    abstract val model: BaseViewModel<T>
    protected var isNetworkAvailable: Boolean = true

    abstract fun renderData(appstate: ru.andreysozonov.model.data.data.AppState)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subscribeToNetworkChange()
    }

    private fun subscribeToNetworkChange() {
        OnlineLiveData(this).observe(this@BaseActivity, Observer<Boolean> {isNetworkAvailable = it
            if (!isNetworkAvailable) {
                Toast.makeText(
                    this@BaseActivity,
                    R.string.dialog_message_device_is_offline,
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }
}


