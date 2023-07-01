package mad.app.madandroidtestsolutions.domain.usecase

import mad.app.madandroidtestsolutions.repository.CatalogRepository
import javax.inject.Inject

class GetProductDetailUseCase @Inject constructor(private val catalogRepository: CatalogRepository) {
}