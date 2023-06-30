package mad.app.madandroidtestsolutions.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import mad.app.madandroidtestsolutions.presentation.screens.SplashScreen
import mad.app.madandroidtestsolutions.service.ApiService

class MainActivity : AppCompatActivity() {

    val apiService = ApiService.createEcommerceClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launchWhenResumed {

            //First, we grab the root level categories from the e-commerce platform
            val rootItems = apiService.catalog.fetchRootCategory()
            Log.d("CatalogExample", "These are the root level Categories we have available:\n" +
                    "${
                        rootItems?.children
                            ?.filterNotNull()
                            ?.map {
                                "${it.name} (uid: ${it.uid})"
                            }
                            ?.joinToString(separator = "\n")
                    }"
            )

            //Lets grab the mens Category UUID and fetch the products for the first page
            val mensCategoryId = rootItems
                ?.children
                ?.find { it?.name?.lowercase() == "mens" }
                ?.uid

            val firstPageMensCat = mensCategoryId?.let { catId ->
                apiService.catalog.getCategory(categoryId = catId)
            }

            Log.d("CatalogExample",
                "The first page of mens category with CatId ${mensCategoryId} has these products:\n" +
                        "${
                            firstPageMensCat?.data?.products?.items
                                ?.map {
                                    "${it?.name} (uid: ${it?.productListFragment?.uid}) and SKU ${it?.productListFragment?.sku}"
                                }
                                ?.joinToString(separator = "\n")
                        }")


            //Now lets fetch the first product from the first page of the Mens Category
            val firstProductUid = firstPageMensCat?.let {
                it.data?.products?.items?.firstOrNull()?.productListFragment?.uid
            }

            val firstProduct = firstProductUid?.let { uid ->
                apiService.catalog.getProduct(uid)
            }

            Log.d(
                "CatalogExample",
                "The first product is ${firstProduct?.productFragment?.name} with SKU ${firstProduct?.productFragment?.sku}\n" +
                        "This product costs at least " +
                        "R${firstProduct?.productFragment?.productListFragment?.price_range?.priceRangeFragment?.minimum_price?.final_price?.value}"
            )
        }

        setContent {
            Surface(color = Color.White, modifier = Modifier.fillMaxSize()) {
                Navigation()
            }
        }
    }
}
@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController,
            startDestination = "splash_screen") {
        composable("splash_screen") {
            SplashScreen(navController = navController)
        }

        // Main Screen
        composable("main_screen") {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Main Screen", color = Color.Black, fontSize = 24.sp)
            }
        }
    }
}