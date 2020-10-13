package ru.andreysozonov.dictionary.di

import dagger.Module
import dagger.Provides
import ru.andreysozonov.dictionary.model.data.SearchResult
import ru.andreysozonov.dictionary.model.datasource.DataSource
import ru.andreysozonov.dictionary.model.datasource.RetrofitImplementation
import ru.andreysozonov.dictionary.model.datasource.RoomDataBaseImplementation
import ru.andreysozonov.dictionary.model.repository.Repository
import ru.andreysozonov.dictionary.model.repository.RepositoryImplementation
import javax.inject.Named
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    @Named(NAME_REMOTE)
    internal fun providesRepositoryRemote(@Named(NAME_REMOTE) dataSourceRemote: DataSource<List<SearchResult>>): Repository<List<SearchResult>> =
        RepositoryImplementation(dataSourceRemote)

    @Provides
    @Singleton
    @Named(NAME_LOCAL)
    internal fun providesRepositoryLocal(@Named(NAME_LOCAL) dataSourceLocal: DataSource<List<SearchResult>>): Repository<List<SearchResult>> =
        RepositoryImplementation(dataSourceLocal)

    @Provides
    @Singleton
    @Named(NAME_REMOTE)
    internal fun provideDataSourceRemote(): DataSource<List<SearchResult>> =
        RetrofitImplementation()

    @Provides
    @Singleton
    @Named(NAME_LOCAL)
    internal fun providesDataSourceLocal(): DataSource<List<SearchResult>> =
        RoomDataBaseImplementation()
}