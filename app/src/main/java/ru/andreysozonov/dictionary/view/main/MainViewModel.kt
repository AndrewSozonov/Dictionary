package ru.andreysozonov.dictionary.view.main

import androidx.lifecycle.LiveData
import io.reactivex.observers.DisposableObserver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.andreysozonov.dictionary.model.data.AppState
import ru.andreysozonov.dictionary.view.viewmodel.BaseViewModel
import javax.inject.Inject

class MainViewModel (private val interactor: MainInteractor) :
    BaseViewModel<AppState>() {


    private var appState: AppState? = null


    override fun getData(word: String, isOnline: Boolean): LiveData<AppState> {
        liveDataForViewToObserve.value = AppState.Loading(null)
        cancelJob()
        viewModelCoroutineScope.launch {
            startInteractor(word, isOnline)
        }
        return liveDataForViewToObserve
    }

    private suspend fun startInteractor(word: String, isOnline: Boolean) = withContext(Dispatchers.IO) {
        liveDataForViewToObserve.postValue(interactor.getData(word, isOnline))
    }

    override fun handleError(error: Throwable) {
      liveDataForViewToObserve.postValue(AppState.Error(error))
    }

    override fun onCleared() {
        liveDataForViewToObserve.value = AppState.Success(null)
        super.onCleared()
    }
}
