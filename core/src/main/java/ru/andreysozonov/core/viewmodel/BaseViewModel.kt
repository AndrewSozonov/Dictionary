package ru.andreysozonov.core.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import ru.andreysozonov.model.data.data.AppState

abstract class BaseViewModel<T : ru.andreysozonov.model.data.data.AppState>(
    protected val liveDataForViewToObserve: MutableLiveData<T> = MutableLiveData()

    ) : ViewModel() {

    protected val viewModelCoroutineScope = CoroutineScope(
        Dispatchers.Main
                + SupervisorJob()
                + CoroutineExceptionHandler { _, throwable ->
            handleError(throwable)
        }
    )

    open fun getData(word: String, isOnline: Boolean): LiveData<T> = liveDataForViewToObserve

    override fun onCleared() {
        super.onCleared()
        cancelJob()
    }

    abstract fun handleError(error: Throwable)

    protected fun cancelJob() {
        viewModelCoroutineScope.coroutineContext.cancelChildren()
    }
}