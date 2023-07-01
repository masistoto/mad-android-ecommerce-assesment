package mad.app.madandroidtestsolutions.service.catalog
import com.apollographql.apollo3.api.ApolloResponse
import mad.app.plptest.CategoryQuery
import mad.app.plptest.CategoryRootQuery
import mad.app.plptest.ProductQuery

interface ICatalogApiService {

    // Fetches root category for E-commerce platform
    suspend fun fetchRootCategory(): CategoryRootQuery.CategoryList?

    // Fetches category and all metadata for category based on uid
    //Please note, page numbers start at 1
    suspend fun getCategory(
        categoryId: String,
        pageNumber: Int = 1,
        pageSize: Int = 20
    ): ApolloResponse<CategoryQuery.Data>

    // Fetches all products for category. Wrapper around `getCategory`
    suspend fun getProductsForCategory(
        categoryId: String,
        pageNumber: Int,
        pageSize: Int
    ): CategoryQuery.Products?

    // Gets detailed information on a particular product, given a uid
    suspend fun getProduct(productUid: String): ProductQuery.Product?

}