package mad.app.madandroidtestsolutions.service

import com.apollographql.apollo3.ApolloClient

class ApiService {
    fun getApolloClient() : ApolloClient {
        return  ApolloClient.Builder()
            .addHttpHeader("store", "app_en_za")
            .serverUrl("https://apiprd.omni.mrpg.com/graphql")
            .build()
    }
}