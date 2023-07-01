package mad.app.madandroidtestsolutions.repository

import com.apollographql.apollo3.api.ApolloResponse
import mad.app.plptest.CategoryQuery
import mad.app.plptest.CategoryRootQuery
import mad.app.plptest.ProductQuery

interface CatalogRepository {
    suspend fun fetchRootCategory(): CategoryRootQuery.CategoryList?

    suspend fun getCategory(
        categoryId: String,
        pageNumber: Int = 1,
        pageSize: Int = 20
    ): ApolloResponse<CategoryQuery.Data>

    suspend fun getProductsForCategory(
        categoryId: String,
        pageNumber: Int,
        pageSize: Int
    ): CategoryQuery.Products?

    suspend fun getProductDetail(productUid: String): ProductQuery.Product?
}