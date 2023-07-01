package mad.app.madandroidtestsolutions.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import mad.app.madandroidtestsolutions.presentation.screens.SplashScreen
import mad.app.madandroidtestsolutions.presentation.screens.main_screen.MainScreen

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launchWhenResumed {

            //First, we grab the root level categories from the e-commerce platform
//            val rootItems = catalogRepository.fetchRootCategory()
//            Log.d("CatalogExample", "These are the root level Categories we have available:\n" +
//                    "${
//                        rootItems?.children
//                            ?.filterNotNull()
//                            ?.map {
//                                "${it.name} (uid: ${it.uid})"
//                            }
//                            ?.joinToString(separator = "\n")
//                    }"
//            )
//
//            //Lets grab the mens Category UUID and fetch the products for the first page
//            val mensCategoryId = rootItems
//                ?.children
//                ?.find { it?.name?.lowercase() == "mens" }
//                ?.uid
//
//            val firstPageMensCat = mensCategoryId?.let { catId ->
//                catalogRepository.getCategory(categoryId = catId)
//            }
//
//            Log.d("CatalogExample",
//                "The first page of mens category with CatId ${mensCategoryId} has these products:\n" +
//                        "${
//                            firstPageMensCat?.data?.products?.items
//                                ?.map {
//                                    "${it?.name} (uid: ${it?.productListFragment?.uid}) and SKU ${it?.productListFragment?.sku}"
//                                }
//                                ?.joinToString(separator = "\n")
//                        }")
//
//
//            //Now lets fetch the first product from the first page of the Mens Category
//            val firstProductUid = firstPageMensCat?.let {
//                it.data?.products?.items?.firstOrNull()?.productListFragment?.uid
//            }
//
//            val firstProduct = firstProductUid?.let { uid ->
//                catalogRepository.getProductDetail(uid)
//            }
//
//            Log.d(
//                "CatalogExample",
//                "The first product is ${firstProduct?.productFragment?.name} with SKU ${firstProduct?.productFragment?.sku}\n" +
//                        "This product costs at least " +
//                        "R${firstProduct?.productFragment?.productListFragment?.price_range?.priceRangeFragment?.minimum_price?.final_price?.value}"
//            )
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
            MainScreen()
        }
    }
}