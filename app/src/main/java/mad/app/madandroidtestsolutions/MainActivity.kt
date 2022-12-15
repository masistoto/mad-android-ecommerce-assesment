package mad.app.madandroidtestsolutions

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import mad.app.madandroidtestsolutions.service.ApiService

class MainActivity : AppCompatActivity() {

    val apiService = ApiService.createEcommerceClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
                apiService.catalog?.getProduct(uid)
            }

            Log.d("CatalogExample",
                "The first product is ${firstProduct?.productFragment?.name} with SKU ${firstProduct?.productFragment?.sku}\n" +
                        "This product costs at least " +
                        "R${firstProduct?.productFragment?.productListFragment?.price_range?.priceRangeFragment?.minimum_price?.final_price?.value}"
            )

        }
    }
}