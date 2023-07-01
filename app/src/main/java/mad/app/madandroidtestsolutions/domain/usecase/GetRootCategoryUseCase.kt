package mad.app.madandroidtestsolutions.domain.usecase

import mad.app.madandroidtestsolutions.repository.CatalogRepository
import javax.inject.Inject

class GetRootCategoryUseCase @Inject constructor(private val catalogRepository: CatalogRepository) {
}