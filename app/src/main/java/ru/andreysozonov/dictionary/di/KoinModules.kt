package ru.andreysozonov.dictionary.di

import RepositoryImplementationLocal
import androidx.room.Room
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.andreysozonov.repository.datasource.RetrofitImplementation
import ru.andreysozonov.repository.datasource.RoomDataBaseImplementation
import ru.andreysozonov.dictionary.model.repository.Repository
import ru.andreysozonov.dictionary.model.repository.RepositoryImplementation
import ru.andreysozonov.dictionary.model.repository.RepositoryLocal
import ru.andreysozonov.dictionary.view.main.MainActivity
import ru.andreysozonov.repository.room.HistoryDataBase
import ru.andreysozonov.dictionary.view.main.MainInteractor
import ru.andreysozonov.dictionary.view.main.MainViewModel

fun injectDependencies() = loadModules

private val loadModules by lazy {
    loadKoinModules(listOf(application, mainScreen))
}


val application = module {
    single { Room.databaseBuilder(get(), HistoryDataBase::class.java, "HistoryDB").build() }
    single { get<HistoryDataBase>().historyDao() }

    single<Repository<List<ru.andreysozonov.model.data.data.SearchResult>>> {
        RepositoryImplementation(RetrofitImplementation())
    }
    single<RepositoryLocal<List<ru.andreysozonov.model.data.data.SearchResult>>> {
        RepositoryImplementationLocal(RoomDataBaseImplementation(get()))
    }
}

val mainScreen = module {
    scope(named<MainActivity>()) {
        viewModel { MainViewModel(get()) }
        scoped { MainInteractor(get(), get()) }
    }

}

