package mad.app.madandroidtestsolutions.domain.usecase

import com.apollographql.apollo3.api.ApolloResponse
import kotlinx.coroutines.flow.flow
import mad.app.madandroidtestsolutions.domain.DataSourceException
import mad.app.madandroidtestsolutions.domain.Resource
import mad.app.madandroidtestsolutions.repository.CatalogRepository
import mad.app.plptest.CategoryQuery
import javax.inject.Inject

class GetCategoryUseCase @Inject constructor(private val catalogRepository: CatalogRepository) {

    operator fun invoke(categoryId: String): kotlinx.coroutines.flow.Flow<Resource<ApolloResponse<CategoryQuery.Data>?>> = flow {
        try {
            emit(Resource.Loading)
            // TODO give this a proper variable name
            val categories = catalogRepository.getCategory(categoryId)
            emit(Resource.Success(data = categories))
        } catch (e: Exception) {
            emit(Resource.Error(DataSourceException.Unexpected(e.localizedMessage)))
        }
    }
}