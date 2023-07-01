package mad.app.madandroidtestsolutions.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mad.app.madandroidtestsolutions.repository.CatalogRepository
import mad.app.madandroidtestsolutions.repository.CatalogRepositoryImp
import mad.app.madandroidtestsolutions.service.catalog.CatalogApiService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideCatalogRepository(catalogApiService: CatalogApiService): CatalogRepository {
        return CatalogRepositoryImp(catalogApiService)
    }
}