package ru.andreysozonov.dictionary.view.main

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import ru.andreysozonov.dictionary.model.data.AppState
import ru.andreysozonov.dictionary.model.datasource.DataSourceLocal
import ru.andreysozonov.dictionary.model.datasource.DataSourceRemote
import ru.andreysozonov.dictionary.model.repository.RepositoryImplementation
import ru.andreysozonov.dictionary.presenter.Presenter
import ru.andreysozonov.dictionary.rx.SchedulerProvider
import ru.andreysozonov.dictionary.view.base.View

class MainPresenterImpl<T : AppState, V : View>(
    private val interactor: MainInteractor = MainInteractor(
        RepositoryImplementation(DataSourceRemote()),
        RepositoryImplementation(DataSourceLocal())
    ),
    protected val compositeDisposable: CompositeDisposable = CompositeDisposable(),
    protected val schedulerProvider: SchedulerProvider = SchedulerProvider()
) : Presenter<T, V> {

    private var currentView: V? = null
    override fun attachView(view: V) {
        if (view != currentView) {
            currentView = view
        }
    }

    override fun deatchView(view: V) {
        compositeDisposable.clear()
        if (view == currentView) {
            currentView = null
        }
    }

    override fun getData(word: String, isOnline: Boolean) {
        compositeDisposable.add(
            interactor.getData(word, isOnline)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .doOnSubscribe {
                    currentView?.renderData(AppState.Loading(null))
                }
                .subscribeWith(getObserver())
        )
    }

    private fun getObserver(): DisposableObserver<AppState> {
        return object : DisposableObserver<AppState>() {
            override fun onComplete() {
            }

            override fun onNext(appState: AppState) {
                currentView?.renderData(appState)
            }

            override fun onError(e: Throwable) {
                currentView?.renderData(AppState.Error(e))
            }

        }
    }
}