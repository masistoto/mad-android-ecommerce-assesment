package mad.app.madandroidtestsolutions.service

import com.apollographql.apollo3.ApolloClient
import mad.app.madandroidtestsolutions.service.catalog.CatalogApiService
import mad.app.madandroidtestsolutions.service.catalog.ICatalogApiService

class ApiService(val apolloClient: ApolloClient) : IApiService {
    override val catalog: ICatalogApiService = CatalogApiService(apolloClient)

    companion object {
        fun createEcommerceClient() : IApiService {
            val apolloClient: ApolloClient = ApolloClient.Builder()
                .addHttpHeader("store", "app_en_za")
                .serverUrl("https://apiprd.omni.mrpg.com/graphql")
                .build()
            return ApiService(apolloClient)
        }
    }
}