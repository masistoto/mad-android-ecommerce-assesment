package mad.app.madandroidtestsolutions.domain.usecase

import kotlinx.coroutines.flow.flow
import mad.app.madandroidtestsolutions.domain.DataSourceException
import mad.app.madandroidtestsolutions.domain.Resource
import mad.app.madandroidtestsolutions.repository.CatalogRepository
import mad.app.plptest.ProductQuery
import javax.inject.Inject

class GetProductDetailUseCase @Inject constructor(private val catalogRepository: CatalogRepository) {

    operator fun invoke(productUid: String): kotlinx.coroutines.flow.Flow<Resource<ProductQuery.Product?>> = flow {
        try {
            emit(Resource.Loading)
            val productDetail = catalogRepository.getProductDetail(productUid)
            emit(Resource.Success(data = productDetail))
        } catch (e: Exception) {
            emit(Resource.Error(DataSourceException.Unexpected(e.localizedMessage)))
        }
    }
}