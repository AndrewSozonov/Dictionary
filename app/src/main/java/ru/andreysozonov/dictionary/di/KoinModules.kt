package ru.andreysozonov.dictionary.di

import RepositoryImplementationLocal
import androidx.room.Room
import org.koin.dsl.module
import ru.andreysozonov.dictionary.model.data.SearchResult
import ru.andreysozonov.dictionary.model.datasource.RetrofitImplementation
import ru.andreysozonov.dictionary.model.datasource.RoomDataBaseImplementation
import ru.andreysozonov.dictionary.model.repository.Repository
import ru.andreysozonov.dictionary.model.repository.RepositoryImplementation
import ru.andreysozonov.dictionary.model.repository.RepositoryLocal
import ru.andreysozonov.dictionary.room.HistoryDataBase
import ru.andreysozonov.dictionary.view.history.HistoryInteractor
import ru.andreysozonov.dictionary.view.history.HistoryViewModel
import ru.andreysozonov.dictionary.view.main.MainInteractor
import ru.andreysozonov.dictionary.view.main.MainViewModel


val application = module {
    single { Room.databaseBuilder(get(), HistoryDataBase::class.java, "HistoryDB").build() }
    single { get<HistoryDataBase>().historyDao() }

    single<Repository<List<SearchResult>>> {
        RepositoryImplementation(RetrofitImplementation())
    }
    single<RepositoryLocal<List<SearchResult>>> {
        RepositoryImplementationLocal(RoomDataBaseImplementation(get()))
    }
}

val mainScreen = module {
    factory { MainViewModel(get()) }
    factory { MainInteractor(get(), get()) }

}

val historyScreen = module {
    factory { HistoryViewModel(get()) }
    factory { HistoryInteractor(get(), get()) }
}
