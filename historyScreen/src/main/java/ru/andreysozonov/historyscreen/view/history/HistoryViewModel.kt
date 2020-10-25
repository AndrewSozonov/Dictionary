package ru.andreysozonov.historyscreen.view.history

import androidx.lifecycle.LiveData
import kotlinx.coroutines.launch
import ru.andreysozonov.core.viewmodel.BaseViewModel
import ru.andreysozonov.model.data.data.AppState
import ru.andreysozonov.utils.utils.parseLocalSearchResults

class HistoryViewModel(private val interactor: HistoryInteractor) : BaseViewModel<AppState>() {


    //val liveDataForViewToObserve: LiveData<AppState> = _mutableLiveData

    fun subscribe(): LiveData<AppState> {
        return liveDataForViewToObserve
    }

    override fun getData(word: String, isOnline: Boolean): LiveData<AppState> {
        liveDataForViewToObserve.value = AppState.Loading(null)
        cancelJob()
        viewModelCoroutineScope.launch { startInteractor(word, isOnline) }
        return liveDataForViewToObserve
    }

    private suspend fun startInteractor(word: String, isOnline: Boolean) {
        liveDataForViewToObserve.postValue(
            parseLocalSearchResults(
                interactor.getData(
                    word,
                    isOnline
                )
            )
        )
    }

    override fun handleError(error: Throwable) {
        liveDataForViewToObserve.postValue(AppState.Error(error))
    }

    override fun onCleared() {
        liveDataForViewToObserve.value = AppState.Success(null)
        super.onCleared()
    }


}