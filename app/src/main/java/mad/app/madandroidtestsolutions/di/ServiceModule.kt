package mad.app.madandroidtestsolutions.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mad.app.madandroidtestsolutions.service.ApiService
import mad.app.madandroidtestsolutions.service.catalog.CatalogApiService
import mad.app.madandroidtestsolutions.service.catalog.ICatalogApiService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    @Provides
    @Singleton
    fun provideCatalogApiService(apiService: ApiService): ICatalogApiService {
        return CatalogApiService(apiService)
    }

    @Singleton
    @Provides
    fun provideApiService() = ApiService()
}