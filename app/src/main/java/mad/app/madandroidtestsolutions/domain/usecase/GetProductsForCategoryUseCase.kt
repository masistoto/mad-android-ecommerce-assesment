package mad.app.madandroidtestsolutions.domain.usecase

import kotlinx.coroutines.flow.flow
import mad.app.madandroidtestsolutions.domain.DataSourceException
import mad.app.madandroidtestsolutions.domain.Resource
import mad.app.madandroidtestsolutions.repository.CatalogRepository
import mad.app.plptest.CategoryQuery
import javax.inject.Inject

class GetProductsForCategoryUseCase @Inject constructor(private val catalogRepository: CatalogRepository) {
    operator fun invoke(categoryId: String, pageNumber: Int, pageSize: Int): kotlinx.coroutines.flow.Flow<Resource<CategoryQuery.Products?>> = flow {
        try {
            emit(Resource.Loading)
            val productsForCategory = catalogRepository.getProductsForCategory(categoryId, pageNumber, pageSize)
            emit(Resource.Success(data = productsForCategory))
        } catch (e: Exception) {
            emit(Resource.Error(DataSourceException.Unexpected(e.localizedMessage)))
        }
    }
}