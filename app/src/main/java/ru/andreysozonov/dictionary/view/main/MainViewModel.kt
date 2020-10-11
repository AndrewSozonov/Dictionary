package ru.andreysozonov.dictionary.view.main

import androidx.lifecycle.LiveData
import io.reactivex.observers.DisposableObserver
import ru.andreysozonov.dictionary.model.data.AppState
import ru.andreysozonov.dictionary.view.viewmodel.BaseViewModel
import javax.inject.Inject

class MainViewModel @Inject constructor(private val interactor: MainInteractor) :
    BaseViewModel<AppState>() {


    private var appState: AppState? = null

    override fun getData(word: String, isOnline: Boolean): LiveData<AppState> {
        compositeDisposable.add(
            interactor.getData(word, isOnline)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .doOnSubscribe { liveDataForViewToObserve.value = AppState.Loading(null) }
                .subscribeWith(getObserver())
        )
        return super.getData(word, isOnline)
    }

    private fun getObserver(): DisposableObserver<AppState> {
        return object : DisposableObserver<AppState>() {
            override fun onComplete() {
            }

            override fun onNext(t: AppState) {
                appState = t
                liveDataForViewToObserve.value = t
            }

            override fun onError(e: Throwable) {
                liveDataForViewToObserve.value = AppState.Error(e)
            }
        }
    }
}
