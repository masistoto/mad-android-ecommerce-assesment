package mad.app.madandroidtestsolutions.service.catalog
import com.apollographql.apollo3.api.ApolloResponse
import mad.app.plptest.CategoryQuery
import mad.app.plptest.CategoryRootQuery
import mad.app.plptest.ProductQuery

interface ICatalogApiService {
    suspend fun fetchRootCategory(): CategoryRootQuery.CategoryList?

    //Please note, page numbers start at 1
    suspend fun getCategory(
        categoryId: String,
        pageNumber: Int = 1,
        pageSize: Int = 20
    ): ApolloResponse<CategoryQuery.Data>

    suspend fun getProduct(productUid: String): ProductQuery.Product?
    suspend fun getProductsForCategory(
        categoryId: String,
        pageNumber: Int,
        pageSize: Int
    ): CategoryQuery.Products?
}