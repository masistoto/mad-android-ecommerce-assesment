package mad.app.madandroidtestsolutions.repository

import mad.app.plptest.CategoryQuery
import mad.app.plptest.CategoryRootQuery
import mad.app.plptest.ProductQuery

interface CatalogRepository {
    suspend fun fetchRootCategory(): CategoryRootQuery.CategoryList?

    suspend fun getProductsForCategory(
        categoryId: String,
        pageNumber: Int,
        pageSize: Int
    ): CategoryQuery.Products?

    suspend fun getProductDetail(productUid: String): ProductQuery.Product?
}