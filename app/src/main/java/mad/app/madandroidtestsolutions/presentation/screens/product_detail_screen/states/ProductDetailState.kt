package mad.app.madandroidtestsolutions.presentation.screens.product_detail_screen.states

import mad.app.madandroidtestsolutions.presentation.screens.BaseState
import mad.app.plptest.ProductQuery

data class ProductDetailState(
    override var isLoading: Boolean = false,
    val productDetail: ProductQuery.Product? = null,
    override var errorMessage: String = ""
) : BaseState()
