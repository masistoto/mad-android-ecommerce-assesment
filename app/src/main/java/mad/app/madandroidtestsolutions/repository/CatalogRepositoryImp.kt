package mad.app.madandroidtestsolutions.repository

import mad.app.madandroidtestsolutions.service.catalog.ICatalogApiService
import mad.app.plptest.CategoryQuery
import mad.app.plptest.CategoryRootQuery
import mad.app.plptest.ProductQuery
import javax.inject.Inject

class CatalogRepositoryImp @Inject constructor(
    private val catalogApiService: ICatalogApiService
) : CatalogRepository {

    override suspend fun fetchRootCategory(): CategoryRootQuery.CategoryList? {
        return catalogApiService.fetchRootCategory()
    }

    override suspend fun getProductsForCategory(
        categoryId: String,
        pageNumber: Int,
        pageSize: Int
    ): CategoryQuery.Products? {
        return  catalogApiService.getProductsForCategory(categoryId = categoryId, pageNumber = pageNumber, pageSize = pageSize)
    }

    override suspend fun getProductDetail(productUid: String): ProductQuery.Product? {
        TODO("Not yet implemented")
    }

}