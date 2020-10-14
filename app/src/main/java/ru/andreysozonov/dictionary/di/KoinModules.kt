package ru.andreysozonov.dictionary.di

import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.andreysozonov.dictionary.model.data.SearchResult
import ru.andreysozonov.dictionary.model.datasource.RetrofitImplementation
import ru.andreysozonov.dictionary.model.datasource.RoomDataBaseImplementation
import ru.andreysozonov.dictionary.model.repository.Repository
import ru.andreysozonov.dictionary.model.repository.RepositoryImplementation
import ru.andreysozonov.dictionary.view.main.MainInteractor
import ru.andreysozonov.dictionary.view.main.MainViewModel

val application = module {
    single<Repository<List<SearchResult>>>(named(NAME_REMOTE)) {
        RepositoryImplementation(RetrofitImplementation())
    }
    single<Repository<List<SearchResult>>>(named(NAME_LOCAL)) {
        RepositoryImplementation(RoomDataBaseImplementation())
    }
}

val mainScreen = module {
    factory { MainInteractor(get(named(NAME_REMOTE)), get(named(NAME_LOCAL))) }
    factory { MainViewModel(get()) }
}