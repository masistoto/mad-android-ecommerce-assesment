package mad.app.madandroidtestsolutions.domain.usecase

import kotlinx.coroutines.flow.flow
import mad.app.madandroidtestsolutions.domain.DataSourceException
import mad.app.madandroidtestsolutions.domain.Resource
import mad.app.madandroidtestsolutions.repository.CatalogRepository
import mad.app.plptest.CategoryRootQuery
import javax.inject.Inject

class GetRootCategoryUseCase @Inject constructor(private val catalogRepository: CatalogRepository) {

    operator fun invoke(): kotlinx.coroutines.flow.Flow<Resource<CategoryRootQuery.CategoryList?>> = flow {
        try {
            emit(Resource.Loading)
            val rootCategories = catalogRepository.fetchRootCategory()
            emit(Resource.Success(data = rootCategories))
        } catch (e: Exception) {
            emit(Resource.Error(DataSourceException.Unexpected(e.localizedMessage)))
        }
    }
}