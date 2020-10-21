package ru.andreysozonov.dictionary.view.main

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.andreysozonov.core.viewmodel.BaseViewModel

class MainViewModel(private val interactor: MainInteractor) :
    BaseViewModel<ru.andreysozonov.model.data.data.AppState>() {


    private var appState: ru.andreysozonov.model.data.data.AppState? = null


    override fun getData(
        word: String,
        isOnline: Boolean
    ): LiveData<ru.andreysozonov.model.data.data.AppState> {
        liveDataForViewToObserve.value = ru.andreysozonov.model.data.data.AppState.Loading(null)
        cancelJob()
        viewModelCoroutineScope.launch {
            startInteractor(word, isOnline)
        }
        return liveDataForViewToObserve
    }

    private suspend fun startInteractor(word: String, isOnline: Boolean) =
        withContext(Dispatchers.IO) {
            liveDataForViewToObserve.postValue(interactor.getData(word, isOnline))
        }

    override fun handleError(error: Throwable) {
        liveDataForViewToObserve.postValue(ru.andreysozonov.model.data.data.AppState.Error(error))
    }

    override fun onCleared() {
        liveDataForViewToObserve.value = ru.andreysozonov.model.data.data.AppState.Success(null)
        super.onCleared()
    }
}
